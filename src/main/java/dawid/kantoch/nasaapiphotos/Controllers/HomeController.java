package dawid.kantoch.nasaapiphotos.Controllers;

import dawid.kantoch.nasaapiphotos.Repos.ApodRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController
{
    private ApodRepo apodRepo;

    public HomeController(ApodRepo apodRepo)
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
