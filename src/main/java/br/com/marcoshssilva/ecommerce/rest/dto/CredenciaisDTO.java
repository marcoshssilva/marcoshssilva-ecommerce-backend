package br.com.marcoshssilva.ecommerce.rest.dto;

import java.io.Serial;
import java.io.Serializable;

public class CredenciaisDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    
    private String email;
    private String senha;

    public CredenciaisDTO() {
    }

    public CredenciaisDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}
