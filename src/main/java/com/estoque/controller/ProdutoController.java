package com.estoque.controller;

import com.estoque.model.Produto;
import com.estoque.repository.ProdutoRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return produtoRepository.save(produto);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Produto buscarPorId(@PathVariable Long id) {
        return produtoRepository.findById(id).orElseThrow();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody Produto novoProduto) {
        Produto existente = produtoRepository.findById(id).orElseThrow();
        existente.setNome(novoProduto.getNome());
        existente.setPreco(novoProduto.getPreco());
        existente.setQuantidade(novoProduto.getQuantidade());
        existente.setCategoria(novoProduto.getCategoria());
        return produtoRepository.save(existente);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        produtoRepository.deleteById(id);
    }

    @GetMapping("/baixo-estoque")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Produto> listarProdutosComEstoqueBaixo(@RequestParam(defaultValue = "5") int limite) {
        return produtoRepository.findByQuantidadeLessThanEqual(limite);
    }

    @GetMapping("/filtro")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Produto> filtrarPorNomeECategoria(@RequestParam(required = false) String nome,
                                                  @RequestParam(required = false) Long categoriaId) {
        return produtoRepository.buscarPorNomeECategoria(nome, categoriaId);
    }
}
