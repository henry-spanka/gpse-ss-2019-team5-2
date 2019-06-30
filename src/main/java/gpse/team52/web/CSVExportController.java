package gpse.team52.web;

import gpse.team52.contract.RoomService;
import gpse.team52.domain.DataExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class CSVExportController {

    @Autowired
    RoomService roomService;
    /**
     * Show the CSVExport  form to the admin.
     *
     * @return CSVExport view.
     */
    @GetMapping("/csvExport")
    public ModelAndView showCSVDownload() {
        DataExport dataExport = new DataExport(roomService);
        final ModelAndView modelAndView = new ModelAndView("csvExport");
        return modelAndView;
    }

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
        return modelAndView;
    }
}
