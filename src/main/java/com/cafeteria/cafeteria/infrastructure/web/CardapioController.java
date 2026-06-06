package com.cafeteria.cafeteria.infrastructure.web;

import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.in.AdicionarProdutoUseCase;
import com.cafeteria.cafeteria.domain.port.in.BuscarCardapioUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio")
@Tag(name ="Cardápio", description = "Gerenciamento do cardápio da cafeteria")
public class CardapioController {
private final AdicionarProdutoUseCase adicionarProdutoUseCase;
private final BuscarCardapioUseCase buscarCardapioUseCase;

public CardapioController(BuscarCardapioUseCase  buscarCardapioUseCase, AdicionarProdutoUseCase adicionarProdutoUseCase){
    this.buscarCardapioUseCase =  buscarCardapioUseCase;
    this.adicionarProdutoUseCase =  adicionarProdutoUseCase;
}
@Operation(summary = "Buscar todos os produtos do cardápio")
@GetMapping
    public List<Produto>buscarTodos(){
    return  this.buscarCardapioUseCase.buscarTodos();
}
@Operation(summary = "Buscar Cardápio por ID")
@GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable UUID id){
    return buscarCardapioUseCase.buscarPorId(id);

}
@Operation(summary="Adicionar produto ao Cardápio")
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Produto adicionarProduto(@Valid @RequestBody AdicionarProdutoUseCase.ProdutoRequestAdd produto){
    return adicionarProdutoUseCase.adicionarProduto(produto);
}


}
