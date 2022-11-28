package br.com.marcoshssilva.ecommerce.domain.services.data;

import br.com.marcoshssilva.ecommerce.domain.entities.Endereco;
import br.com.marcoshssilva.ecommerce.domain.repositories.EnderecoRepository;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.ObjectNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    EnderecoRepository repo;

    public Endereco buscar(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }

    public Endereco inserir(Endereco e) {
        return repo.save(e);
    }
}
