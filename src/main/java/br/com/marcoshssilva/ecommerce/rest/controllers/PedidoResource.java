package br.com.marcoshssilva.ecommerce.rest.controllers;

import java.net.URI;

import javax.validation.Valid;

import br.com.marcoshssilva.ecommerce.domain.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.marcoshssilva.ecommerce.domain.entities.Pedido;

@RequestMapping(value = "/pedidos")
@RestController
public class PedidoResource {

    @Autowired
    private PedidoService serv;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok().body(this.serv.listarPorId(id));
    }

    @PostMapping()
    public ResponseEntity<Void> cadastrar(
            @Valid @RequestBody Pedido obj
    ) {
        Pedido o = serv.cadastrar(obj);
        URI u = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(o.getId()).toUri();
        return ResponseEntity.created(u).build();
    }

    @GetMapping()
    public ResponseEntity<Page<Pedido>> buscaPaginada(
            @RequestParam(value = "page", defaultValue = "0") Integer npage,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy
    ) {
        return ResponseEntity.ok(this.serv.encontrarPagina(npage, linesPerPage, direction, orderBy));
    }
}
