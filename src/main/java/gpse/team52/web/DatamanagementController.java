package gpse.team52.web;

import gpse.team52.domain.DataImport;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class DatamanagementController {

    private static String UPLOADED_FOLDER = "C:\\Users\\stell\\Desktop\\UniBielefeld\\SS19";


    /**
     * Show the datamanagement form to the user.
     *
     * @return Datamanagement view.
     */
    @GetMapping("/csvImport")
    public ModelAndView showDatamanagement() {
        final ModelAndView modelAndView = new ModelAndView("csvImport");
        return modelAndView;
    }

    @PostMapping("/csvImport")
    public ModelAndView submitData( @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        //check if there is an uploaded file
       if (file != null && !file.isEmpty()) {
           //check wich radio btn is enabled is needed
           DataImport dataImport = new DataImport();
           dataImport.csvImport(file);
           return new ModelAndView("redirect:/start");
        }if (file == null){

        }
        return new ModelAndView("redirect:/csvImport");
    }
}

