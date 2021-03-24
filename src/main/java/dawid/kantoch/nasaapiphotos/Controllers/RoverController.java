package dawid.kantoch.nasaapiphotos.Controllers;

import dawid.kantoch.nasaapiphotos.Repos.DataRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoverController
{
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
}
