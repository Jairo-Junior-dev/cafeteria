package com.cafeteria.cafeteria.infrastructure.web.filter;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cafeteria.cafeteria.domain.port.out.RateLimiterPort;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimiterPort rateLimiter;

    // Construtor manual único e limpo para injeção do Spring
    public RateLimitFilter(RateLimiterPort rateLimiter) {
        this.rateLimiter = rateLimiter;
        System.out.println(">>> [RateLimit] RateLimitFilter construído com sucesso! RateLimit = " + rateLimiter);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String key = resolveKey(request);

                if (rateLimiter.isAllowed(key)) {
            chain.doFilter(request, response);
        } else {
            long wait = rateLimiter.getWaitTimeInSeconds(key);
            
            // Define o status correto
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setHeader("Retry-After", String.valueOf(wait));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            // Escreve a resposta no buffer
            response.getWriter().write("""
                {"erro": "Muitas requisições. Tente novamente em %d segundos."}
                """.formatted(wait));
            
            // CORREÇÃO CRUCIAL: Força o buffer a enviar os dados imediatamente 
            // e fechar o canal de comunicação para que o Spring Security não interfira mais
            response.getWriter().flush();
            response.getWriter().close();
            return;
        } 
    
    }


    private String resolveKey(HttpServletRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null
                && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return "user:" + auth.getName();
        }
        return "ip:" + request.getRemoteAddr();
    }
}
