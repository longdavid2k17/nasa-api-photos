package dawid.kantoch.nasaapiphotos.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EONETController
{
    @GetMapping("/eonet")
    public String eonet()
    {
        return "eonet";
    }
}
