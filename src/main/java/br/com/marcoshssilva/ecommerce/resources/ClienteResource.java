package br.com.marcoshssilva.ecommerce.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import br.com.marcoshssilva.ecommerce.services.ClienteService;
import br.com.marcoshssilva.ecommerce.services.UserService;
import br.com.marcoshssilva.ecommerce.services.exceptions.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.marcoshssilva.ecommerce.domain.Cliente;
import br.com.marcoshssilva.ecommerce.domain.Endereco;
import br.com.marcoshssilva.ecommerce.dto.ClienteDTO;
import br.com.marcoshssilva.ecommerce.dto.ClienteNewDTO;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService serv;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> listar(@PathVariable Integer id) {
        return ResponseEntity.ok().body(serv.buscar(id));
    }

    @GetMapping(value = "/email")
    public ResponseEntity<?> listar(@RequestParam(value = "value") String email) {
        return ResponseEntity.ok().body(serv.encontrarPeloEmail(email));
    }

    @PostMapping
    public ResponseEntity<Void> inserir(@Valid @RequestBody ClienteNewDTO c) {
        Cliente obj = serv.inserir(serv.fromDto(c));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> atualizar(@RequestBody Cliente c, @PathVariable Integer id) {
        c.setId(id);
        serv.atualizar(c);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        serv.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> buscarTodos() {
        List<Cliente> l = serv.buscarTodos();
        List<ClienteDTO> lDto = l.stream().map(item -> new ClienteDTO(item)).collect(Collectors.toList());
        return ResponseEntity.ok().body(lDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<List<ClienteDTO>> encontrarPagina(@RequestParam(value = "row", defaultValue = "0") Integer npagina, @RequestParam(value = "linesPerPage", defaultValue = "24") Integer quantidade, @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        List<ClienteDTO> l = serv.encontrarPagina(npagina, quantidade, orderBy, direction).map(i -> new ClienteDTO(i)).toList();
        return ResponseEntity.ok().body(l);
    }

    @PostMapping(value = "/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
        URI uri = serv.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping(value = "/endereco")
    public ResponseEntity<Endereco> cadastrarEndereco(@RequestBody Endereco e) {

        if (UserService.authenticated() == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");
        }

        Cliente cli = serv.buscar(UserService.authenticated().getUsername());
        Endereco end = this.serv.cadastrarEndereco(cli, e);

        return ResponseEntity.ok(end);
    }

    @DeleteMapping(value = "/endereco/{id}")
    public ResponseEntity<?> apagarEndereco(@PathVariable() Integer id) {
        if (UserService.authenticated() == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");
        }
        Cliente cli = serv.buscar(UserService.authenticated().getUsername());
        serv.apagarEndereco(cli, id);

        return ResponseEntity.noContent().build();
    }
}
