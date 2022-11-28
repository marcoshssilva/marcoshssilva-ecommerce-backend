package br.com.marcoshssilva.ecommerce.domain.repositories;

import br.com.marcoshssilva.ecommerce.domain.entities.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

}
