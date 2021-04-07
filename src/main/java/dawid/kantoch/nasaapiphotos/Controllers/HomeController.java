package dawid.kantoch.nasaapiphotos.Controllers;

import dawid.kantoch.nasaapiphotos.Repos.APODRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    private Logger log = LoggerFactory.getLogger(HomeController.class);

    private APODRepo apodRepo;

    public HomeController(APODRepo apodRepo)
    {
        this.apodRepo = apodRepo;
    }

    @GetMapping("/")
    public String getIndex(Model model)
    {
        model.addAttribute("apodList",apodRepo.getApodList());
        return "index";
    }
}
