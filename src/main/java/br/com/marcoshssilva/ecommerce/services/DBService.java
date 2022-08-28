package br.com.marcoshssilva.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.marcoshssilva.ecommerce.domain.Cliente;
import br.com.marcoshssilva.ecommerce.domain.enums.Perfil;
import br.com.marcoshssilva.ecommerce.repositories.ClienteRepository;

@Service
public class DBService {

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    private BCryptPasswordEncoder bcrypt;

    public void instanciateDatabase() {
        // Cadastrando um usuário ADMIN
        Cliente cli = new Cliente(null, "Marcos Henrique", "marcoshssilva@email.com.br", bcrypt.encode("12345678910"));
        cli.addPerfil(Perfil.ADMIN);
    }
}
