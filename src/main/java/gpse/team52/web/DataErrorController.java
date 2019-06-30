package gpse.team52.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class DataErrorController {
    @GetMapping("/dataError")
    public ModelAndView errorCSVUpload() {
        final ModelAndView modelAndView = new ModelAndView("dataError");
        return modelAndView;
    }

    @PostMapping("/dataError")
    public ModelAndView solveError( RedirectAttributes redirectAttributes) {
        try {
            return new ModelAndView("redirect:/csvImport");
        }catch (Exception e){
        return new ModelAndView("redirect:/csvImport");
    }
}}
