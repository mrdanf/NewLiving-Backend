package com.newliving.backend.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Bestätigung Ihrer Registrierung");
            helper.setFrom("info@newliving.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Konnte Email nicht senden", e);
            throw new IllegalStateException("Konnte Email nicht senden");
        }
    }

    public String buildEmailRegistration(String name) {
        return  "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hallo " + name + ",</p>" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Vielen Dank für ihre Registrierung.</p>" +
                "<blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"></p></blockquote>\n" +
                "<p>Falls Sie diese Registierung nicht vorgenommen haben, können Sie diese Email einfach ignorieren.</p>"
                ;
    }

    public String buildEmailPasswortReset(String name, String tempPassword) {
        return "<p>Hallo " + name + ",</p>" +
                "<p> Sie haben die Zurücksetzung ihres Passworts beantragt. Ihr Passwort wurde zurückgesetzt auf " +
                "folgendes:</p>" +
                "<p>Neues Passwort: " + tempPassword + " </p>" +
                "<p>Wir empfehlen Ihnen, das Passwort nach der Anmeldung sofort zu ändern.</p>";
    }
}
