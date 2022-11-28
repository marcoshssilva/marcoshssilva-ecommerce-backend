package br.com.marcoshssilva.ecommerce.rest.controllers;

import br.com.marcoshssilva.ecommerce.domain.services.CategoriaService;
import br.com.marcoshssilva.ecommerce.domain.entities.Categoria;
import br.com.marcoshssilva.ecommerce.rest.dto.CategoriaDTO;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService serv;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Categoria> listar(@PathVariable Integer id) {
        return ResponseEntity.ok().body(serv.buscar(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> inserir(@Valid @RequestBody CategoriaDTO c) {
        Categoria obj = serv.inserir(serv.fromDto(c));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(obj.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> atualizar(@RequestBody Categoria c, @PathVariable Integer id) {
        c.setId(id);
        serv.atualizar(c);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        serv.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> buscarTodos() {
        List<Categoria> l = serv.buscarTodos();
        List<CategoriaDTO> lDto = l.stream().map(item -> new CategoriaDTO(item)).collect(Collectors.toList());

        return ResponseEntity.ok().body(lDto);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoriaDTO>> encontrarPagina(@RequestParam(value = "row", defaultValue = "0") Integer npagina, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer quantidade, @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<CategoriaDTO> l = serv.encontrarPagina(npagina, quantidade, orderBy, direction).map(i -> new CategoriaDTO(i));
        return ResponseEntity.ok().body(l);
    }

}
