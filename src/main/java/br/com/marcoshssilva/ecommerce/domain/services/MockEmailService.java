package br.com.marcoshssilva.ecommerce.domain.services;


import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractMailMessage{

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
    
    @Override
    public void sendEmail(SimpleMailMessage smm) {
        LOG.info("Simulando envio de email...");
        LOG.info(smm.toString());
        LOG.info("Email enviado com sucesso!");
    }
    
    @Override
    public void sendHtmlEmail(MimeMessage mm) {
        LOG.info("Simulando envio de email...");
        LOG.info(mm.toString());
        LOG.info("Email enviado com sucesso!");
    }
}
