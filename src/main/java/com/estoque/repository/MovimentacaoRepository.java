package com.estoque.repository;

import com.estoque.model.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.estoque.dto.RelatorioResumoProdutoDTO;
import org.springframework.data.jpa.repository.Query;




public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByProdutoIdOrderByDataDesc(Long produtoId);
    List<Movimentacao> findByDataBetweenOrderByDataDesc(LocalDate inicio, LocalDate fim);
    List<Movimentacao> findByDataBetweenOrderByDataDesc(LocalDateTime inicio, LocalDateTime fim);
    
    @Query("SELECT new com.estoque.dto.RelatorioResumoProdutoDTO(" +
    	       "p.nome, " +
    	       "SUM(CASE WHEN m.tipo = 'ENTRADA' THEN m.quantidade ELSE 0 END), " +
    	       "SUM(CASE WHEN m.tipo = 'SAIDA' THEN m.quantidade ELSE 0 END)) " +
    	       "FROM Movimentacao m JOIN m.produto p " +
    	       "GROUP BY p.nome")
    	List<RelatorioResumoProdutoDTO> gerarResumoPorProduto();


}

