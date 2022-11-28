package br.com.marcoshssilva.ecommerce.domain.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.marcoshssilva.ecommerce.domain.entities.Categoria;
import br.com.marcoshssilva.ecommerce.rest.dto.CategoriaDTO;
import br.com.marcoshssilva.ecommerce.domain.repositories.CategoriaRepository;
import br.com.marcoshssilva.ecommerce.domain.services.exceptions.DataIntegrityException;
import br.com.marcoshssilva.ecommerce.domain.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    public Categoria buscar(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cidade não encontrada."));
    }

    public Categoria inserir(Categoria c) {
        c.setId(null);
        return repo.save(c);
    }

    public Categoria atualizar(Categoria c) {
        Categoria newObj = buscar(c.getId());
        updateData(newObj, c);
        return repo.save(newObj);
    }

    public void apagar(Integer id) {
        try {
            repo.deleteById(id);

        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não pode excluir esta categoria, porque existem produtos associados a ela.");

        }

    }

    public List<Categoria> buscarTodos() {
        return repo.findAll();
    }

    public Page<Categoria> encontrarPagina(Integer npagina, Integer quantidade, String orderBy, String direction) {
        PageRequest pr = PageRequest.of(npagina, quantidade, Direction.fromString(direction), orderBy);
        return repo.findAll(pr);
    }

    public Categoria fromDto(CategoriaDTO dto) {
        return new Categoria(dto.getId(), dto.getNome(), dto.getImageUrl());
    }

    private void updateData(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }
}
