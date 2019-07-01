package gpse.team52.contract.mail;

import gpse.team52.domain.Candidate;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.mail.MailException;
import org.springframework.web.servlet.ModelAndView;

/**
 * Mail Service Contract.
 * Responsible for sending out Mails.
 */
public interface MailService {
    /**
     * Sends an email to the given email address.
     *
     * @param email   The email to send the mail to.
     * @param subject The email subject.
     * @param message The message (body).
     * @param html    Whether the content type should be html or plain text.
     * @throws MailException Thrown if the message could not be sent.
     */
    void sendEmailMessage(String email, String subject,
                          String message, boolean html) throws MailException;


    void sendEmailMessageToUser(User user, String subject, String message, boolean html) throws MailException;

    void sendEmailTemplateToUser(User user, String subject, ModelAndView template) throws MailException;

    void sendEmailTemplate(Participant participant,
                           String subject, ModelAndView template) throws MailException;

     void sendEmailToCAndidate(Candidate candidate, String subject, ModelAndView template) throws MailException;
}
