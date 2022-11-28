package br.com.marcoshssilva.ecommerce.domain.services;

import java.util.Date;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.domain.entities.Pedido;

public abstract class AbstractMailMessage implements EmailService {

    @Value("${spring.mail.username}")
    private String sender;

    @Value("${default.recipient}")
    private String recipient;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Envio de email texto puro
     * @param obj
     */
    @Override
    public void sendOrderConfirmationEmail(Pedido obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
        sendEmail(sm);
    }

    /**
     * Envio de email processado pelo thymeleaf/html
     * @param obj
     */
    @Override
    public void sendOrderConfirmationHtmlEmail(Pedido obj) {
        try {
            MimeMessage mm = prepareMimeMessageFromPedido(obj, obj.getCliente().getEmail());
            MimeMessage ma = prepareMimeMessageFromPedido(obj, recipient);
            // Email para o sistema
            sendHtmlEmail(ma);
            // Email para o cliente
            sendHtmlEmail(mm);
        } catch (MessagingException e) {
            sendOrderConfirmationEmail(obj);
        }
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido o) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(o.getCliente().getEmail(), recipient);
        smm.setFrom(sender);
        smm.setSubject("CONFIRMAÇÃO DE PEDIDO! Nº" + o.getId());
        smm.setText(o.toString());
        return smm;
    }

    private SimpleMailMessage prepareNewPasswordEmail(Cliente c, String newPass) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(c.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Solicitação de Nova Senha");
        sm.setText("Nova senha: " + newPass);
        return sm;
    }

    @Override
    public void sendNewPasswordEmail(Cliente c, String newPass) {
        SimpleMailMessage sm = prepareNewPasswordEmail(c, newPass);
        sendEmail(sm);
    }

    private MimeMessage prepareMimeMessageFromPedido(Pedido obj, String destination) throws MessagingException {
        MimeMessage mm = javaMailSender.createMimeMessage();
        MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
        mmh.setTo(destination);
        mmh.setFrom(sender);
        mmh.setSubject("CONFIRMAÇÃO DE PEDIDO! Nº" + obj.getId());
        mmh.setSentDate(new Date(System.currentTimeMillis()));
        mmh.setText(htmlFromTemplatePedido(obj), true);

        return mm;
    }

    protected String htmlFromTemplatePedido(Pedido obj) {
        Context c = new Context();
        c.setVariable("pedido", obj);
        return templateEngine.process("email/confirmacaoPedido", c);
    }
}
