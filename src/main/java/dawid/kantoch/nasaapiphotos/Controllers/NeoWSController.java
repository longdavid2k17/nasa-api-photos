package dawid.kantoch.nasaapiphotos.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NeoWSController
{
    @GetMapping("/neows")
    public String neoWS()
    {
        return "neows";
    }
}
