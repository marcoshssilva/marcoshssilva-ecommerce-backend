package br.com.marcoshssilva.ecommerce.domain.services.data;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

import br.com.marcoshssilva.ecommerce.domain.services.auth.UserService;
import br.com.marcoshssilva.ecommerce.domain.services.etc.ImageService;
import br.com.marcoshssilva.ecommerce.domain.services.etc.S3Service;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.AuthorizationException;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.DataIntegrityException;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.domain.entities.Endereco;
import br.com.marcoshssilva.ecommerce.domain.enums.Perfil;
import br.com.marcoshssilva.ecommerce.rest.dto.ClienteDTO;
import br.com.marcoshssilva.ecommerce.rest.dto.ClienteNewDTO;
import br.com.marcoshssilva.ecommerce.domain.repositories.ClienteRepository;
import br.com.marcoshssilva.ecommerce.domain.repositories.EnderecoRepository;
import br.com.marcoshssilva.ecommerce.core.configs.security.UserDetailsImpl;

@Service
public class ClienteService {

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    @Autowired
    ClienteRepository repo;

    @Autowired
    EnderecoRepository enderecoRepo;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    @Autowired
    private S3Service s3service;

    @Autowired
    private ImageService imageService;

    public Cliente buscar(Integer id) {
        UserDetailsImpl u = UserService.authenticated();
        if (u == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");

        } else if (!u.hasRole(Perfil.ADMIN) && !id.equals(u.getId())) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");
        }

        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }

    public Cliente buscar(String email) {
        UserDetailsImpl u = UserService.authenticated();
        if (u == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");

        } else if (!u.hasRole(Perfil.ADMIN) && !email.equals(u.getUsername())) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");
        }

        return repo.findByEmail(email);
    }

    public Cliente inserir(Cliente c) {
        c.setId(null);
        Cliente obj = repo.save(c);
        obj.getEnderecos().forEach(e -> enderecoRepo.save(e));
        return obj;
    }

    public Cliente atualizar(Cliente c) {
        UserDetailsImpl u = UserService.authenticated();
        if (u == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");

        } else if (!u.hasRole(Perfil.ADMIN) && !c.getId().equals(u.getId())) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");
        }

        Cliente cli = repo.findByEmail(u.getUsername());

        updateData(cli, c);

        c.getEnderecos().forEach(e -> {
            e.setCliente(cli);
            enderecoRepo.save(e);
        });

        return repo.save(cli);
    }

    public void apagar(Integer id) {
        UserDetailsImpl u = UserService.authenticated();
        if (u == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");

        } else if (!u.hasRole(Perfil.ADMIN) && !id.equals(u.getId())) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");
        }

        try {
            repo.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não pode excluir esta Cliente, porque existem pedidos relacionadas a este cliente");

        }

    }

    public List<Cliente> buscarTodos() {
        return repo.findAll();
    }

    public Cliente encontrarPeloEmail(String email) {

        UserDetailsImpl u = UserService.authenticated();
        if (u == null) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");

        } else if (!u.hasRole(Perfil.ADMIN) && !email.equals(u.getUsername())) {
            throw new AuthorizationException("O usuário NÃO esta logado ou NÃO possui autorização");

        }

        Cliente obj = repo.findByEmail(email);

        if (obj == null) {
            throw new ObjectNotFoundException("Objeto não encontrado! Id: " + u.getId());
        }

        return obj;
    }

    public Page<Cliente> encontrarPagina(Integer npagina, Integer quantidade, String orderBy, String direction) {
        PageRequest pr = PageRequest.of(npagina, quantidade, Sort.Direction.fromString(direction), orderBy);
        return repo.findAll(pr);
    }

    public Cliente fromDto(ClienteDTO d) {
        return new Cliente(d.getId(), d.getNome(), d.getEmail(), null);
    }

    public Cliente fromDto(ClienteNewDTO d) {
        Cliente cli = new Cliente(null, d.getNome(), d.getEmail(), bcrypt.encode(d.getSenha()));
        cli.setCpf(d.getCpf());

        if (d.getCidade() != null && d.getEstado() != null && d.getBairro() != null && d.getCep() != null && d.getNumero() != null && d.getLogradouro() != null) {
            Endereco end = new Endereco(null, d.getLogradouro(), d.getNumero(), d.getComplemento(), d.getBairro(), d.getCep(), cli, d.getCidade(), d.getEstado());
            cli.getEnderecos().add(end);
        }

        cli.getTelefones().add(d.getTelefone1());

        if (d.getTelefone2() != null) {
            cli.getTelefones().add(d.getTelefone2());
        }

        if (d.getTelefone3() != null) {
            cli.getTelefones().add(d.getTelefone3());
        }

        return cli;
    }

    private void updateData(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setTelefones(obj.getTelefones());
        newObj.setCpf(obj.getCpf());
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserDetailsImpl user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso negado");
        }

        BufferedImage jpgImage = imageService.getImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);
        String fileName = prefix + user.getId() + ".jpg";

        URI imageUrl = s3service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image/jpeg");

        Cliente c = this.buscar(user.getUsername());
        c.setImageUrl(imageUrl.toString());
        this.repo.save(c);

        return imageUrl;
    }

    public Endereco cadastrarEndereco(Cliente c, Endereco e) {
        Cliente cli = this.buscar(c.getId());
        cli.getEnderecos().add(e);

        e.setCliente(cli);
        Endereco end = this.enderecoRepo.save(e);
        this.repo.save(cli);

        return end;
    }

    public void apagarEndereco(Cliente c, Integer id) {
        Endereco e = this.enderecoRepo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Este cliente não possui este endereço"));

        if (c.getEnderecos().contains(e)) {
            c.getEnderecos().remove(e);
            
            this.repo.save(c);
            this.enderecoRepo.deleteById(e.getId());
            
        } else {
            throw new ObjectNotFoundException("Este cliente não possui este endereço");
            
        }

    }
}
