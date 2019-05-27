package gpse.team52.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Dashboard.
 */
@Controller
public class DashboardController {
    @GetMapping("/dashboard")
    public ModelAndView dashboard() {

        return new ModelAndView("dashboard");
    }
}
