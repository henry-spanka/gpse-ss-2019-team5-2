package gpse.team52.web;

import gpse.team52.contract.UserService;
import gpse.team52.contract.mail.MailService;
import gpse.team52.domain.DataImport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * CSV Upload Controller.
 */
@RestController
public class CSVUploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;


    /**
     * Show the CSVUpload  form to the admin.
     *
     * @return CSVUpload view.
     */
    @GetMapping("/csvImport")
    public ModelAndView showCSVUpload() {
        final ModelAndView modelAndView = new ModelAndView("csvImport");
        return modelAndView; //NOPMD
    }

    /**
     * Import a CSV File.
     * @param file Multipart File.
     * @param redirectAttributes RedirectAttributes.
     * @return ModelAndView.
     */
    @PostMapping("/csvImport")
    public ModelAndView submitData(final @RequestParam("file") MultipartFile file, final RedirectAttributes redirectAttributes) {
        try {
            //check if there is an uploaded file
            if (file != null && !file.isEmpty()) {

                final DataImport dataImport = new DataImport(userService, mailService);
                dataImport.csvImport(file);

            }
            if (file == null) {
                return new ModelAndView("redirect:/dataError");
            }
        } catch (Exception e) { //NOPMD
            return new ModelAndView("redirect:/dataError");
        }
        return new ModelAndView("redirect:/start");
    }
}

