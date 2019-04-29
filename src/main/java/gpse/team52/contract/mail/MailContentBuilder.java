package gpse.team52.contract.mail;

import org.springframework.web.servlet.ModelAndView;

/**
 * MailContentBuilder contract.
 * Responsible for parsing Thymeleaf templates to html.
 */
public interface MailContentBuilder {
    /**
     * Renders HTML from Thymeleaf templates.
     * @param modelAndView The ModelAndView object that should be rendered.
     * @return The rendered HTML as a String.
     */
    String build(ModelAndView modelAndView);
}
