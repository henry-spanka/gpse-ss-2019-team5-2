package gpse.team52.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * DataErrorController.
 */
@RestController
public class DataErrorController {
    /**
     * CSV Error Upload.
     * @return ModelAndView.
     */
    @GetMapping("/dataError")
    public ModelAndView errorCSVUpload() {
        final ModelAndView modelAndView = new ModelAndView("dataError");
        return modelAndView; //NOPMD
    }

    /**
     * CSV Error Solver.
     * @param redirectAttributes Redirect Attributes.
     * @return ModelAndView.
     */
    @PostMapping("/dataError")
    public ModelAndView solveError(final RedirectAttributes redirectAttributes) {
        try {
            return new ModelAndView("redirect:/csvImport");
        } catch (Exception e) { //NOPMD
            return new ModelAndView("redirect:/csvImport");
        }
    }
}
