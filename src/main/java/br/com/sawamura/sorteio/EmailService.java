package br.com.sawamura.sorteio;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author rkendy
 */
public class EmailService {

    private static String EMAIL_REMETENTE_KEY = "EMAIL_REMETENTE_SORTEIO";
    private static String EMAIL_SENHA_KEY = "EMAIL_SENHA_SORTEIO";

    private String titulo;
    private String remetente;
    private String senha;

    public EmailService(String titulo) {
        this.titulo = titulo;

        this.remetente = System.getenv(EMAIL_REMETENTE_KEY);
        this.senha = System.getenv(EMAIL_SENHA_KEY);

        if (remetente == null || senha == null) {
            System.out.println("Configure o email do remetente e a senha nas variaveis de ambiente:");
            System.out.println("\texport " + EMAIL_REMETENTE_KEY + "=email");
            System.out.println("\texport " + EMAIL_SENHA_KEY + "=senha");
            System.out.println("Para gerar senha, ver https://myaccount.google.com/lesssecureapps");
            System.exit(1);
        }
    }

    private Properties getProperties() {
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");
        return props;
    }

    public void envia(String email, String texto) {

        // Para configurar senha/acesso:
        // https://myaccount.google.com/lesssecureapps
        // https://support.google.com/accounts/answer/185833
        Session session = Session.getInstance(getProperties(), new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha);
            }
        });

        /** Ativa Debug para sessão */
        session.setDebug(false);

        try {
            

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remetente));

            message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));

            message.setSubject(this.titulo); // Assunto

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(texto, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            System.out.print("Enviando email... ");
            Transport.send(message);
            System.out.println("Done!!!");

        } catch (MessagingException e) {
            System.out.println("Erro ao enviar email " + e);
            throw new RuntimeException(e);
        }
    }
}
