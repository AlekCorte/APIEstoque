package com.estoque.service;

import com.estoque.dto.MovimentacaoRequest;
import com.estoque.model.*;
import com.estoque.repository.MovimentacaoRepository;
import com.estoque.repository.ProdutoRepository;
import com.estoque.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovimentacaoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MovimentacaoRepository movimentacaoRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MovimentacaoService movimentacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simula usuário autenticado no contexto
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken("admin", null, List.of());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Test
    void testRegistrarEntradaEstoque() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setQuantidade(5);

        User usuario = new User();
        usuario.setUsername("admin");

        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));

        MovimentacaoRequest request = new MovimentacaoRequest();
        request.setProdutoId(1L);
        request.setTipo(TipoMovimentacao.ENTRADA);
        request.setQuantidade(3);

        movimentacaoService.registrarMovimentacao(request);

        assertEquals(8, produto.getQuantidade());
        verify(movimentacaoRepository, times(1)).save(any(Movimentacao.class));
    }
}
