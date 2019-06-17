package gpse.team52.web;

import gpse.team52.contract.PositionService;
import gpse.team52.contract.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RightAdministrationController {
    @Autowired
    private PositionService positionService;
    private RightService rightService;

    public RightAdministrationController() {
    }

    /**
     * @return Page with available rooms to choose from
     */
    @GetMapping("/rightAdministration")
    public ModelAndView rights() {
        final ModelAndView modelAndView = new ModelAndView("rightsAdministration");
        modelAndView.addObject("positionList", positionService.getAllPositions());
        modelAndView.addObject("rightList", rightService.getAllRights());
        return modelAndView;
    }
}
