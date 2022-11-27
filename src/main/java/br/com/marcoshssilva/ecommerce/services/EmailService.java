package br.com.marcoshssilva.ecommerce.services;

import jakarta.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import br.com.marcoshssilva.ecommerce.domain.Cliente;
import br.com.marcoshssilva.ecommerce.domain.Pedido;


public interface EmailService {
    void sendOrderConfirmationEmail(Pedido o);
    void sendEmail(SimpleMailMessage smm);
    
    void sendOrderConfirmationHtmlEmail(Pedido o);
    void sendHtmlEmail(MimeMessage msg);

    public void sendNewPasswordEmail(Cliente c, String newPass);
}
