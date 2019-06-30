package gpse.team52.web;

import gpse.team52.contract.UserService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.domain.DataImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CSVUploadController {

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    /**
     * Show the CSVUpload  form to the admin.
     *
     * @return CSVUpload view.
     */
    @GetMapping("/csvImport")
    public ModelAndView showCSVUpload() {
        final ModelAndView modelAndView = new ModelAndView("csvImport");
        return modelAndView;
    }

    @PostMapping("/csvImport")
    public ModelAndView submitData(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            //check if there is an uploaded file
            if (file != null && !file.isEmpty()) {

                DataImport dataImport = new DataImport(userService, mailService);
                dataImport.csvImport(file);

            }
            if (file == null) {
                return new ModelAndView("redirect:/dataError");
            }
        } catch (Exception e) {
            return new ModelAndView("redirect:/dataError");
        }
        return new ModelAndView("redirect:/start");
    }
}

