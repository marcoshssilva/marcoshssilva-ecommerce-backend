package br.com.marcoshssilva.ecommerce.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.domain.enums.Perfil;
import br.com.marcoshssilva.ecommerce.domain.repositories.ClienteRepository;

@Service
public class DBService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private PasswordEncoder encoder;

    public void instanciateDatabase() {
        // Cadastrando um usuário ADMIN
        Cliente cli = new Cliente(null, "Marcos Henrique", "marcoshssilva.dev@gmail.com", encoder.encode("12345678910"));
        cli.addPerfil(Perfil.ADMIN);

        clienteRepository.save(cli);
    }
}
