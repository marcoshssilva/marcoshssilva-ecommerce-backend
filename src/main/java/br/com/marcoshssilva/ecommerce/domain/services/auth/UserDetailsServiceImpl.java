package br.com.marcoshssilva.ecommerce.domain.services.auth;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.domain.repositories.ClienteRepository;
import br.com.marcoshssilva.ecommerce.core.configs.security.UserDetailsImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente c = clienteRepository.findByEmail(email);
        if (c == null) {
            throw new UsernameNotFoundException(email);
        }

        return new UserDetailsImpl(c.getId(), c.getEmail(), c.getSenha(), c.getPerfil());
    }

}
