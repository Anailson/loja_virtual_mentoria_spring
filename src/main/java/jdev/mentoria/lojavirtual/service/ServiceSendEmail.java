package jdev.mentoria.lojavirtual.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

@Service
public class ServiceSendEmail {

    private static final Logger logger = LoggerFactory.getLogger(ServiceSendEmail.class);

    // Altere essa senha para a senha de aplicativo gerada no Google
    private String userName = "axxxxx@gmail.com";
    private String senha = "senha_de_aplicativo"; // Usar senha de aplicativo do Gmail aqui

    @Async
    public void enviarEmailHtml(String assunto, String mensagem, String emailDestino) throws UnsupportedEncodingException {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true"); // TLS
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); // Porta para TLS
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

        // Criação da sessão
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, senha); // Autenticação com Gmail
            }
        });

        session.setDebug(true); // Ativar logs de debug para SMTP

        try {
            // Criando a mensagem de e-mail
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(userName, "Anailson - do Java Web", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDestino));
            message.setSubject(assunto);
            message.setContent(mensagem, "text/html; charset=UTF-8"); // Definindo o e-mail como HTML

            // Tentando enviar o e-mail
            Transport.send(message);
            logger.info("E-mail enviado com sucesso para: " + emailDestino); // Log de sucesso

        } catch (MessagingException e) {
            logger.error("Erro ao enviar e-mail: ", e); // Log de erro
        }
    }
}
