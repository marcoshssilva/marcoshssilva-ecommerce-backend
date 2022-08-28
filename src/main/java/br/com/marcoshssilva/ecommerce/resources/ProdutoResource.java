package br.com.marcoshssilva.ecommerce.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcoshssilva.ecommerce.domain.Produto;
import br.com.marcoshssilva.ecommerce.dto.ProdutoDTO;
import br.com.marcoshssilva.ecommerce.resources.utils.URL;
import br.com.marcoshssilva.ecommerce.services.ProdutoService;

@RestController
@RequestMapping(value = "/cursos")
public class ProdutoResource {

    @Autowired
    private ProdutoService serv;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Produto> listar(@PathVariable Integer id) {
        return ResponseEntity.ok().body(serv.buscar(id));
    }

    @GetMapping(value = "/")
    public ResponseEntity<Page<ProdutoDTO>> encontrarPagina(@RequestParam(value = "nome", defaultValue = "") String nome, @RequestParam(value = "categorias", defaultValue = "") String categorias, @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, @RequestParam(value = "direction", defaultValue = "ASC") String direction, @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        List<Integer> ids = URL.decodeIntList(URL.decodeParam(categorias));
        Page<ProdutoDTO> l = serv.buscar(nome, ids, page, linesPerPage, orderBy, direction).map(i -> new ProdutoDTO(i));
        return ResponseEntity.ok().body(l);
    }

    @GetMapping(value = "/destaque")
    public ResponseEntity<Page<ProdutoDTO>> buscarEmDestaque(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, @RequestParam(value = "direction", defaultValue = "ASC") String direction, @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {
        Page<ProdutoDTO> l = serv.buscarEmDestaque(page, linesPerPage, orderBy, direction).map(i -> new ProdutoDTO(i));
        return ResponseEntity.ok(l);
    }
}
