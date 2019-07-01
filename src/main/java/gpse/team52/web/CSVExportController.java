package gpse.team52.web;

import gpse.team52.contract.MeetingService;
import gpse.team52.contract.RoomService;
import gpse.team52.contract.UserService;
import gpse.team52.domain.DataExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * CSV Export Controller.
 */
@RestController
public class CSVExportController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private MeetingService meetingService;

    /**
     * Show the CSVExport  form to the admin.
     *
     * @return CSVExport view.
     */
    @GetMapping("/csvExport")
    public ModelAndView showCSVDownload() {
        final DataExport dataExport = new DataExport(roomService, userService, meetingService); //NOPMD
        final ModelAndView modelAndView = new ModelAndView("csvExport");
        return modelAndView; //NOPMD
    }

    /**
     * Download CSV File.
     * @return ModelAndView.
     */
    @PostMapping("/csvExport")
    public ModelAndView download(/*@PathVariable("file_name") String fileName, HttpServletResponse response*/) {
        /*try {
            // get your file as InputStream
            InputStream is = ;
            // copy it to response's OutputStream
            org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (IOException ex) {
            log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }*/

        final ModelAndView modelAndView = new ModelAndView("csvExport");
        return modelAndView; //NOPMD
    }
}
