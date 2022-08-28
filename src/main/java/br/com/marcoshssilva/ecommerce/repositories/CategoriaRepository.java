package br.com.marcoshssilva.ecommerce.repositories;

import br.com.marcoshssilva.ecommerce.domain.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
