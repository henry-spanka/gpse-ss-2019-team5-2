package gpse.team52.service.mail;

import javax.mail.internet.MimeMessage;

import gpse.team52.contract.mail.MailContentBuilder;
import gpse.team52.contract.mail.MailService;
import gpse.team52.domain.Participant;
import gpse.team52.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

/**
 * Mail Service implementation.
 */
@Service
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final MailContentBuilder mailContentBuilder;
    private final Environment env;

    /**
     * Creates a new MailService.
     *
     * @param mailSender         The JavaMailSender to use.
     * @param mailContentBuilder The ContentBuilder to use for parsing Thymeleaf templates.
     * @param env                The environment for retrieving settings like mailFrom.
     */
    @Autowired
    public MailServiceImpl(final JavaMailSender mailSender, final MailContentBuilder mailContentBuilder,
                           final Environment env) {
        this.mailSender = mailSender;
        this.mailContentBuilder = mailContentBuilder;
        this.env = env;
    }

    /**
     * Sends an email to the given email address.
     *
     * @param email   The email to send the mail to.
     * @param subject The email subject.
     * @param message The message (body).
     * @param html    Whether the content type should be html or plain text.
     * @throws MailException Thrown if the message could not be sent.
     */
   /* public void sendEmailMessage(String email, final String subject, final String message, final boolean html) {
        final MimeMessagePreparator messagePreparator = (MimeMessage mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(env.getRequiredProperty("mail.from"));
            messageHelper.setTo(email);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, html);
        };

        mailSender.send(messagePreparator);
    }*/
    public void sendEmailMessage (final String mail, final String subject, final String message, final boolean html)
    throws MailException {
        final MimeMessagePreparator messagePreparator = (MimeMessage mimeMessage) -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(env.getRequiredProperty("mail.from"));
            messageHelper.setTo(mail);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, html);
        };

        mailSender.send(messagePreparator);
    }


    @Override
    public void sendEmailMessageToUser(final User user, final String subject, final String message, final boolean html)
    throws MailException {
        sendEmailMessage(user.getEmail(), subject, message, html);
    }

    @Override
    public void sendEmailTemplateToUser(final User user, final String subject, final ModelAndView template)
    throws MailException {
        sendEmailMessageToUser(user, subject, mailContentBuilder.build(template), true);
    }

    @Override
    public void sendEmailTemplate(final Participant participant, final String subject, final ModelAndView template) throws MailException {
        sendEmailMessage(participant.getEmail(), subject, mailContentBuilder.build(template), true);
    }
}
