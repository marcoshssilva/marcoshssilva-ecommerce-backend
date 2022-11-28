package br.com.marcoshssilva.ecommerce.core.configs.security;

import br.com.marcoshssilva.ecommerce.domain.exceptions.models.StandardErrorModel;
import br.com.marcoshssilva.ecommerce.rest.dto.CredenciaisDTO;
import br.com.marcoshssilva.ecommerce.rest.utils.JwtUtils;
import br.com.marcoshssilva.ecommerce.domain.exceptions.throwables.AuthorizationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        super.setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {

        try {
            CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());

            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;
            
        } catch (IOException e) {
            throw new AuthorizationException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        String username = ((UserDetailsImpl) auth.getPrincipal()).getUsername();
        String token = jwtUtils.generateToken(username);
        res.addHeader("Authorization", "Bearer " + token);
        res.addHeader("Access-Control-Expose-Headers", "Authorization");

    }

    private static class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            var writer = response.getWriter();

            response.setStatus(
                    HttpStatus.UNAUTHORIZED.value());

            response.setContentType(
                    MediaType.APPLICATION_JSON_VALUE);

            writer.append(this.json(HttpStatus.UNAUTHORIZED));
        }

        private String json(HttpStatus status) throws JsonProcessingException {
            var errorModel = new StandardErrorModel(status.value(), status.getReasonPhrase(), "Email e/ou senha invalidos.", System.currentTimeMillis(), "/login");
            var mapper = new ObjectMapper();

            return mapper.writeValueAsString(errorModel);
        }
    }
}
