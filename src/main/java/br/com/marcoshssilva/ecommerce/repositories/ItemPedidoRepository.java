package br.com.marcoshssilva.ecommerce.repositories;

import br.com.marcoshssilva.ecommerce.domain.ItemPedido;
import br.com.marcoshssilva.ecommerce.domain.ItemPedidoPK;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, ItemPedidoPK>{
    
}
