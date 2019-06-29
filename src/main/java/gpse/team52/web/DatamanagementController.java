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
    @GetMapping("/datamanagement")
    public ModelAndView showDatamanagement() {
        final ModelAndView modelAndView = new ModelAndView("datamanagement");
        return modelAndView;
    }

    @PostMapping("/datamanagement")
    public ModelAndView submitData( @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        //check if there is an uploaded file
       if (file != null && !file.isEmpty()) {
           //check wich radio btn is enabled is needed
           DataImport dataImport = new DataImport();
           dataImport.csvImport(file);

           System.out.println("hallo");
           return new ModelAndView("redirect:/start");
        }if (file == null){

        }

       /*user.setFirstname(createUserCmd.getFirstname());
        user.setLastname(createUserCmd.getLastname());
        user.setLocation(createUserCmd.getLocation());
        userService.updateUser(user);*/
        return new ModelAndView("redirect:/datamanagement");
    }
}

