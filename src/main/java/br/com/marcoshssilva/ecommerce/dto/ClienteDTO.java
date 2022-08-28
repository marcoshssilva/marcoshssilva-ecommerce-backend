package br.com.marcoshssilva.ecommerce.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;

import br.com.marcoshssilva.ecommerce.services.validation.ClienteUpdate;
import org.hibernate.validator.constraints.br.CPF;

import br.com.marcoshssilva.ecommerce.domain.Cliente;

@ClienteUpdate
public class ClienteDTO implements Serializable {

    private static final long serialVersionUID = 1l;

    private Integer id;
    private String nome;

    @Email(message = "O email deve ser válido")
    private String email;

    @CPF(message = "O CPF deve ser válido")
    private String cpf;
    
    private Set<String> telefones = new HashSet<>();

    public ClienteDTO() {}

    public ClienteDTO(Cliente c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.email = c.getEmail();
        this.telefones = c.getTelefones();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getTelefones() {
        return telefones;
    }

    public void setTelefones(Set<String> telefones) {
        this.telefones = telefones;
    }
}
