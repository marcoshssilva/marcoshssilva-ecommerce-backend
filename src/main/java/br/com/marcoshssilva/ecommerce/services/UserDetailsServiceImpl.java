package br.com.marcoshssilva.ecommerce.services;

import br.com.marcoshssilva.ecommerce.domain.Cliente;
import br.com.marcoshssilva.ecommerce.repositories.ClienteRepository;
import br.com.marcoshssilva.ecommerce.security.UserSS;

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

        return new UserSS(c.getId(), c.getEmail(), c.getSenha(), c.getPerfil());
    }

}
