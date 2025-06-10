package com.estoque.service;

import com.estoque.dto.MovimentacaoRequest;
import com.estoque.model.*;
import com.estoque.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MovimentacaoService {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UserRepository userRepository;

    public void registrarMovimentacao(MovimentacaoRequest request) {
        Produto produto = produtoRepository.findById(request.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        if (request.getTipo() == TipoMovimentacao.SAIDA && produto.getQuantidade() < request.getQuantidade()) {
            throw new RuntimeException("Estoque insuficiente para saída");
        }

        // Ajustar quantidade do estoque
        if (request.getTipo() == TipoMovimentacao.ENTRADA) {
            produto.setQuantidade(produto.getQuantidade() + request.getQuantidade());
        } else {
            produto.setQuantidade(produto.getQuantidade() - request.getQuantidade());
        }

        produtoRepository.save(produto);

        // Usuário logado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User usuario = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Movimentacao mov = new Movimentacao();
        mov.setProduto(produto);
        mov.setTipo(request.getTipo());
        mov.setQuantidade(request.getQuantidade());
        mov.setData(LocalDateTime.now());
        mov.setUsuario(usuario);

        movimentacaoRepository.save(mov);
    }
}
