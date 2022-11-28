package br.com.marcoshssilva.ecommerce.domain.services.data;

import java.util.List;

import br.com.marcoshssilva.ecommerce.domain.entities.Categoria;
import br.com.marcoshssilva.ecommerce.domain.entities.Produto;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.ObjectNotFoundException;
import br.com.marcoshssilva.ecommerce.domain.repositories.CategoriaRepository;
import br.com.marcoshssilva.ecommerce.domain.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository repo;

    @Autowired
    CategoriaRepository catRepo;

    public Produto buscar(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }

    public Page<Produto> buscar(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pr = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = catRepo.findAllById(ids);
        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pr);
    }

    public Page<Produto> buscarEmDestaque(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pr = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAllByDestaque(Boolean.TRUE, pr);
    }
}
