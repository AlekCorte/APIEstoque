package com.estoque.repository;

import com.estoque.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByQuantidadeLessThanEqual(int limite);
    

        @Query("SELECT p FROM Produto p WHERE " +
               "(:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))) AND " +
               "(:categoriaId IS NULL OR p.categoria.id = :categoriaId)")
        List<Produto> buscarPorNomeECategoria(@Param("nome") String nome, @Param("categoriaId") Long categoriaId);

        // Produtos com estoque abaixo de um limite
        @Query("SELECT p FROM Produto p WHERE p.quantidade <= :limite")
        List<Produto> comEstoqueBaixo(@Param("limite") int limite);
    }

