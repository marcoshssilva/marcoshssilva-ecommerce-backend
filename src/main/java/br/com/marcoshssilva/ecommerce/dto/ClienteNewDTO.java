package br.com.marcoshssilva.ecommerce.dto;

import java.io.Serial;
import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.com.marcoshssilva.ecommerce.services.validation.ClienteInsert;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

@ClienteInsert
public class ClienteNewDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotEmpty(message = "O nome deve ser fornecido")
    @Length(min = 5, max = 255, message = "O nome deve conter entre 5 e 255 caracteres")
    private String nome;

    @NotEmpty(message = "O email deve ser fornecido")
    @Length(min = 10, max = 255, message = "O email deve conter entre 5 e 255 caracteres")
    @Email(message = "O email deve ser válido")
    private String email;

    @CPF(message = "O CPF fornecido deve ser válido")
    private String cpf;
    
    @NotEmpty(message = "A senha deve ser fornecido")
    @Length(min = 6, max = 40, message = "A senha deve conter entre 6 e 40 caracteres")
    private String senha;

    @NotEmpty(message = "Deve haver ao menos 1 número de telefone")
    private String telefone1;
    private String telefone2;
    private String telefone3;

    private String logradouro;
    private String complemento;
    private String bairro;
    private String numero;
    private String cep;

    private String cidade;
    private String estado;

    public ClienteNewDTO() { }

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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
