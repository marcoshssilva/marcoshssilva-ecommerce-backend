package br.com.marcoshssilva.ecommerce.domain.repositories;

import br.com.marcoshssilva.ecommerce.domain.entities.Endereco;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>{
    
}
