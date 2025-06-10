package com.estoque.controller;

import com.estoque.dto.MovimentacaoRequest;
import com.estoque.dto.RelatorioResumoProdutoDTO;
import com.estoque.model.Movimentacao;
import com.estoque.repository.MovimentacaoRepository;
import com.estoque.service.MovimentacaoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/movimentacoes")
public class MovimentacaoController {

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String registrarMovimentacao(@RequestBody MovimentacaoRequest request) {
        movimentacaoService.registrarMovimentacao(request);
        return "Movimentação registrada com sucesso!";
    }

    @GetMapping("/produto/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<Movimentacao> listarPorProduto(@PathVariable Long id) {
        return movimentacaoRepository.findByProdutoIdOrderByDataDesc(id);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Movimentacao> listarPorPeriodo(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        LocalDateTime dataInicio = inicio.atStartOfDay();
        LocalDateTime dataFim = fim.atTime(LocalTime.MAX);
        return movimentacaoRepository.findByDataBetweenOrderByDataDesc(dataInicio, dataFim);
    }

    @GetMapping("/relatorio/resumo")
    @PreAuthorize("hasRole('ADMIN')")
    public List<RelatorioResumoProdutoDTO> relatorioResumo() {
        return movimentacaoRepository.gerarResumoPorProduto();
    }
}
