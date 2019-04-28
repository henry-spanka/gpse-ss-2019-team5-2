package gpse.team52.helper;

import java.util.Map;

import gpse.team52.contract.mail.MailContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.expression.ThymeleafEvaluationContext;

/**
 * Implementation for the MailContentBuilder for parsing Thymeleaf templates.
 */
@Service
public class MailContentBuilderImpl implements MailContentBuilder {

    private TemplateEngine templateEngine;

    private ApplicationContext applicationContext;

    @Autowired
    public MailContentBuilderImpl(final TemplateEngine templateEngine, final ApplicationContext applicationContext) {
        this.templateEngine = templateEngine;
        this.applicationContext = applicationContext;
    }

    /**
     * Renders HTML from Thymeleaf templates.
     *
     * @param modelAndView The ModelAndView object that should be rendered.
     * @return The rendered HTML as a String.
     */
    public String build(final ModelAndView modelAndView) {
        final Map<String, Object> model = modelAndView.getModelMap();
        final Context context = new Context();
        context.setVariables(model);
        context.setVariable(ThymeleafEvaluationContext.THYMELEAF_EVALUATION_CONTEXT_CONTEXT_VARIABLE_NAME,
        new ThymeleafEvaluationContext(applicationContext, null));

        return templateEngine.process(modelAndView.getViewName(), context);
    }

}
