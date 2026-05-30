package com.cafeteria.cafeteria.infrastructure.web;

import com.cafeteria.cafeteria.domain.model.Produto;
import com.cafeteria.cafeteria.domain.port.in.AdicionarProdutoUseCase;
import com.cafeteria.cafeteria.domain.port.in.BuscarCardapioUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio")
public class CardapioController {
private final AdicionarProdutoUseCase adicionarProdutoUseCase;
private final BuscarCardapioUseCase buscarCardapioUseCase;

public CardapioController(BuscarCardapioUseCase  buscarCardapioUseCase, AdicionarProdutoUseCase adicionarProdutoUseCase){
    this.buscarCardapioUseCase =  buscarCardapioUseCase;
    this.adicionarProdutoUseCase =  adicionarProdutoUseCase;
}
@GetMapping
    public List<Produto>buscarTodos(){
    return  this.buscarCardapioUseCase.buscarTodos();
}
@GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable UUID id){
    return buscarCardapioUseCase.buscarPorId(id);

}
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Produto adicionarProduto(@RequestBody AdicionarProdutoUseCase.ProdutoRequestAdd produto){
    return adicionarProdutoUseCase.adicionarProduto(produto);
}


}
