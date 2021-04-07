package dawid.kantoch.nasaapiphotos.Controllers;

import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Event;
import dawid.kantoch.nasaapiphotos.Repos.EONETRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EONETController
{
    private EONETRepo eonetRepo;
    private List<Event> primaryEvents = null;
    private int specifiedCount = 30;

    public EONETController(EONETRepo eonetRepo)
    {
        this.eonetRepo = eonetRepo;
    }

    @GetMapping("/eonet")
    public String eonet(Model model)
    {
        try
        {
            primaryEvents = eonetRepo.getEvents(specifiedCount);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        model.addAttribute("primaryEventList",primaryEvents);
        return "eonet";
    }

    @GetMapping("/eonet/{count}")
    public String eonetNonDefCountOfResults(@PathVariable(value = "count") Integer count, Model model) throws Exception
    {
        if(count==null || count<=0)
        {
            throw new Exception("Error while passing results count!");
        }
        else
        {
            try
            {
                primaryEvents = eonetRepo.getEvents(count);
                this.specifiedCount = count;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            model.addAttribute("primaryEventList",primaryEvents);
            return "eonet";
        }
    }

    @GetMapping("/eonet/category/{categoryid}")
    public String eonetSpecifiedCategotyResults(@PathVariable(value = "categoryid") Long categoryid, Model model) throws Exception
    {
        List<Event> categorizedEvents = new ArrayList<>();

        if(categoryid==null || categoryid<=0)
        {
            throw new Exception("Error while passing category id!");
        }
        else
        {
            try
            {
                primaryEvents = eonetRepo.getEvents(specifiedCount);
                for(Event event : primaryEvents)
                {
                    if(event.getCategory().getId()==categoryid)
                    {
                        categorizedEvents.add(event);
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            model.addAttribute("primaryEventList",categorizedEvents);
            return "eonet";
        }
    }
}
