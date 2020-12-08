package br.com.sawamura.sorteio;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author rkendy
 */
public class Email {

    private static String EMAIL_REMETENTE = "";
    private static String EMAIL_SENHA_APP = "";

    private Properties props;
    private String titulo;

    public Email(String titulo) {
        this.props = getProperties();
        this.titulo = titulo;
    }

    private Properties getProperties() {
        Properties props = new Properties();
        /** Parâmetros de conexão com servidor Gmail */
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        return props;
    }

    public void envia(String email, String texto) {

        Session session = Session.getDefaultInstance(this.props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                // Para configurar senha/acesso:
                // https://myaccount.google.com/lesssecureapps
                // https://support.google.com/accounts/answer/185833
                return new javax.mail.PasswordAuthentication(EMAIL_REMETENTE, EMAIL_SENHA_APP);
            }
        });

        /** Ativa Debug para sessão */
        session.setDebug(false);

        try {
            System.out.println("Enviando email para " + email);

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_REMETENTE)); // Remetente

            Address[] toUser = InternetAddress.parse(email);

            message.setRecipients(MimeMessage.RecipientType.TO, toUser);
            message.setSubject(this.titulo); // Assunto

            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(texto, "text/html; charset=utf-8");
            messageBodyPart.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
            messageBodyPart.setContent(texto, "text/plain; charset=utf-8");
            messageBodyPart.setHeader("Content-Transfer-Encoding", "quoted-printable");

            Multipart multipart = new MimeMultipart();

            // add the message body to the mime message
            multipart.addBodyPart(messageBodyPart);

            // Put all message parts in the message
            message.setContent(multipart);

            // message.setText(texto);
            // Transport.send(message);

            System.out.println("Email to: " + email);
            System.out.println("Texto: " + texto);

            System.out.println("Done!!!");

        } catch (MessagingException e) {
            System.out.println("Erro ao enviar email " + e);
            throw new RuntimeException(e);
        }
    }
}
