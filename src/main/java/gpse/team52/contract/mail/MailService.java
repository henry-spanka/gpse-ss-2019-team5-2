package gpse.team52.contract.mail;

import gpse.team52.domain.User;
import org.springframework.mail.MailException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Mail Service Contract.
 * Responsible for sending out Mails.
 */
public interface MailService {
    /**
     * Sends an email to the users email address.
     * @param user The user to send the mail to (their email).
     * @param subject The email subject.
     * @param message The message (body).
     * @param html Whether the content type should be html or plain text.
     * @throws MailException Thrown if the message could not be sent.
     */
    void sendEmailMessageToUser(User user, String subject, String message, boolean html) throws MailException;

    /**
     * Sends an email to the users email address from Thymeleaf templates.
     * @param user The User to send the mail to (their email).
     * @param subject The email subject.
     * @param template The Thymeleaf ModelAndView object.
     * @throws MailException Thrown if the message could not be sent.
     */
    void sendEmailTemplateToUser(User user, String subject, ModelAndView template) throws MailException;
}
