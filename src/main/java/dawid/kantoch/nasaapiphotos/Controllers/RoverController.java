package dawid.kantoch.nasaapiphotos.Controllers;

import dawid.kantoch.nasaapiphotos.Models.Photos;
import dawid.kantoch.nasaapiphotos.Repos.DataRepo;
import dawid.kantoch.nasaapiphotos.Services.RoverPicturesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RoverController
{
    private Logger log = LoggerFactory.getLogger(RoverController.class);

    private DataRepo dataRepo;

    public RoverController(DataRepo dataRepo)
    {
        this.dataRepo = dataRepo;
    }

    @GetMapping("/rover")
    public String getPhotos(Model model)
    {
        model.addAttribute("photoList",dataRepo.getPhotosList());
        return "rover";
    }

    @GetMapping("/rover/{cameraId}")
    public String getFilteredPhotos(@PathVariable(value = "cameraId")Long cameraId, Model model)
    {
        log.warn("Filtering!");
        try
        {
            List<Photos> filteredList = dataRepo.sortByCameraId(cameraId);
            log.info("List filtered");
            model.addAttribute("photoList",filteredList);
        }
        catch (Exception e)
        {
            log.error(e.getMessage()+" Sending primary list!");
            model.addAttribute("photoList",dataRepo.getPhotosList());
        }
        return "rover";
    }
}
