package gpse.team52;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WelcomeController {

    @Value("${application.message:Hello World}")
    private String message = "Hello World";

    @RequestMapping("/")
    public ModelAndView welcome() {
        final ModelAndView modelAndView = new ModelAndView("welcome");
        modelAndView.addObject("time", LocalDateTime.now());
        modelAndView.addObject("message", this.message);

        return modelAndView;
    }

}
