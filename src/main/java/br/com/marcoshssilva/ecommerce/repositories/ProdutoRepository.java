package br.com.marcoshssilva.ecommerce.repositories;

import br.com.marcoshssilva.ecommerce.domain.Categoria;
import br.com.marcoshssilva.ecommerce.domain.Produto;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Transactional(readOnly = true)
    Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

    Page<Produto> findAllByDestaque(Boolean opcao, Pageable pageRequest);
}
