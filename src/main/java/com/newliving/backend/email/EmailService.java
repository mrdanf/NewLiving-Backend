package com.newliving.backend.email;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Versendet vorgefertigte Emails an Nutzer nach Registrierung oder Passwort zurücksetzen.
 */
@Service
@AllArgsConstructor
public class EmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender mailSender;

    /**
     * Baut eine MIME Email auf und versendet diese an den Nutzer.
     *
     * Baut eine MIME Email auf mit Subject "Info von NewLiving" und Absender "info@newliving.com". Die Email geht an
     * die übergebene Email Adresse eines Nutzers. Der Inhalt der Email wird an anderer Stelle erstellt und dieser
     * Methode übergeben.
     *
     * @param to Empfänger der Email
     * @param email Inhalt der Email
     */
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Info von NewLiving");
            helper.setFrom("info@newliving.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            LOGGER.error("Konnte Email nicht senden", e);
            throw new IllegalStateException("Konnte Email nicht senden");
        }
    }

    /**
     * Baut eine Email zur Bestätigung einer Registrierung.
     *
     * @param name Der Name des Nutzers, der in der Email angesprochen werden soll
     * @return Emailtext
     */
    public String buildEmailRegistration(String name) {
        return  "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hallo " + name + ",</p>" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Vielen Dank für ihre Registrierung.</p>" +
                "<blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\">" +
                "<p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"></p></blockquote>\n" +
                "<p>Falls Sie diese Registierung nicht vorgenommen haben, können Sie diese Email einfach ignorieren.</p>"
                ;
    }

    /**
     * Baut eine Email mit einem neuen Passwort, nachdem der Nutzer sein Passwort zurückgesetzt hat.
     *
     * @param name Der Name des Nutzers, der in der Email angesprochen werden soll
     * @param tempPassword Das neue Passwort
     * @return Emailtext
     */
    public String buildEmailPasswortReset(String name, String tempPassword) {
        return "<p>Hallo " + name + ",</p>" +
                "<p>Sie haben die Zurücksetzung ihres Passworts beantragt. Ihr Passwort wurde zurückgesetzt auf " +
                "folgendes:</p>" +
                "<p>Neues Passwort: " + tempPassword + " </p>" +
                "<p>Wir empfehlen Ihnen, das Passwort nach der Anmeldung sofort zu ändern.</p>";
    }

    public String buildEmailInvitation(String name, String link) {
        return "<p>Hallo!</p>" +
                "<p>Ihr Freund " + name + " hat Sie zur Hilfe beim Umzug gebeten. Folgen Sie einfach diesem Link, " +
                "wenn Sie die Umzugsplanung Ihres Freundes einsehen möchten:</p>" +
                "<p><a href=\"" + link + "\">Umzugsplanung ansehen</a> </p>" +
                "<p>Wir empfehlen Ihnen, das Passwort nach der Anmeldung sofort zu ändern.</p>";
    }
}
