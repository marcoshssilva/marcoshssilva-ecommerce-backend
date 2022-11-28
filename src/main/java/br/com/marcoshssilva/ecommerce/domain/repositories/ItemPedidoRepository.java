package br.com.marcoshssilva.ecommerce.domain.repositories;

import br.com.marcoshssilva.ecommerce.domain.entities.ItemPedido;
import br.com.marcoshssilva.ecommerce.domain.entities.ItemPedidoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK>{
    
}
