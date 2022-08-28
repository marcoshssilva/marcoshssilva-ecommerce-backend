package br.com.marcoshssilva.ecommerce.dto;

import br.com.marcoshssilva.ecommerce.domain.Categoria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;

public class CategoriaDTO implements Serializable {

    public static final long serialVersionUID = 1l;

    private Integer id;

    @NotEmpty(message = "Este campo deve ser preenchido")
    @Length(max = 80, min = 5, message = "A categoria deve estar entre 5 e 80 caracteres")
    private String nome;

    private String imageUrl;
    private List<ProdutoDTO> produtos = new ArrayList<>();

    public CategoriaDTO() { }

    public CategoriaDTO(Categoria c) {
        this.id = c.getId();
        this.nome = c.getNome();
        this.imageUrl = c.getImageUrl();
        this.produtos = c.getProdutos().stream().map(i -> new ProdutoDTO(i)).collect(Collectors.toList());
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<ProdutoDTO> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoDTO> produtos) {
        this.produtos = produtos;
    }

}
