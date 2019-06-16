package gpse.team52.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import javax.validation.Path;
import javax.validation.Valid;

import gpse.team52.contract.UserService;
import gpse.team52.domain.User;
import gpse.team52.exception.EmailExistsException;
import gpse.team52.exception.InvalidConfirmationTokenException;
import gpse.team52.exception.UsernameExistsException;
import gpse.team52.form.UserRegistrationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryBeanDefinitionRegistrarSupport;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

   /* @PostMapping("/datamanagement")
    public ModelAndView submitData( @RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes) {
        System.out.println("flubb");
        //check if there is an uploaded file
       if (file != null && !file.isEmpty()) {
           // dbFileStorageService.store(file);
           // user.setPicture(file.getOriginalFilename());
           System.out.println("hallo");
        }if (file == null){
           System.out.println("dong");
        }*//*if(file.isEmpty()){
           System.out.println("witch is dead");
        }*//*

       *//* user.setFirstname(createUserCmd.getFirstname());
        user.setLastname(createUserCmd.getLastname());
        user.setLocation(createUserCmd.getLocation());
        userService.updateUser(user);*//*
        return new ModelAndView("redirect:/start");
    }*/
    @PostMapping("/datamanagement")
    public ModelAndView dataManager(@RequestParam("file") MultipartFile file,
                                    RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return new ModelAndView("redirect:/start");
        }

        try {

           /* // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            redirectAttributes.addFlashAttribute("message",
            "You successfully uploaded '" + file.getOriginalFilename() + "'");*/
           System.out.println("hallo");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/start");
    }
}

