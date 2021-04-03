package dawid.kantoch.nasaapiphotos.Controllers;

import dawid.kantoch.nasaapiphotos.Models.NeoWSDate;
import dawid.kantoch.nasaapiphotos.Repos.NeoWSRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class NeoWSController
{
    private Logger log = LoggerFactory.getLogger(NeoWSController.class);
    private NeoWSRepo repo;

    public NeoWSController(NeoWSRepo repo)
    {
        this.repo = repo;
    }

    @GetMapping("/neows")
    public String neoWS(Model model)
    {
        model.addAttribute("dates",new NeoWSDate());
        return "neows";
    }

    @PostMapping("/neows")
    public String searchForObject(@Valid NeoWSDate dates, Model model) throws Exception
    {
        log.info("Recived dates: from -> "+dates.getDateFrom()+" ; to -> "+dates.getDateTo());
        if(dates==null)
        {
            log.error("No data passed by form!");
        }
        else
            repo.getServiceData(dates);
            model.addAttribute("dates",dates);
            model.addAttribute("nearEarthObjectList",repo.getNearEarthObjectList());
            log.info("Passed "+repo.getNearEarthObjectList().size()+" objects to view!");
            return "neows";
    }
}
