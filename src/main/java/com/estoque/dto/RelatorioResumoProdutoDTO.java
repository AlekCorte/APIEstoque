package com.estoque.dto;

public class RelatorioResumoProdutoDTO {

    private String nomeProduto;
    private Long totalEntradas;
    private Long totalSaidas;

    // Construtor necessário para @Query new
    public RelatorioResumoProdutoDTO(String nomeProduto, Long totalEntradas, Long totalSaidas) {
        this.nomeProduto = nomeProduto;
        this.totalEntradas = totalEntradas;
        this.totalSaidas = totalSaidas;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Long getTotalEntradas() {
        return totalEntradas;
    }

    public void setTotalEntradas(Long totalEntradas) {
        this.totalEntradas = totalEntradas;
    }

    public Long getTotalSaidas() {
        return totalSaidas;
    }

    public void setTotalSaidas(Long totalSaidas) {
        this.totalSaidas = totalSaidas;
    }
}
