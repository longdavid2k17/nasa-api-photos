package dawid.kantoch.nasaapiphotos.Repos;

import dawid.kantoch.nasaapiphotos.Models.NearEarthObject;
import dawid.kantoch.nasaapiphotos.Models.NeoWSDate;
import dawid.kantoch.nasaapiphotos.Services.NeoWSService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class NeoWSRepo
{
    private Logger log = LoggerFactory.getLogger(NeoWSRepo.class);
    private List<NearEarthObject> nearEarthObjects;
    private NeoWSService neoWSService;

    public NeoWSRepo(NeoWSService neoWSService)
    {
        this.neoWSService = neoWSService;
        this.nearEarthObjects = new ArrayList<>();
    }

    public void getServiceData(NeoWSDate neoWSDate) throws Exception
    {
        try
        {
            log.warn("Sending request!");
            nearEarthObjects = getNeoWSService().getData(neoWSDate);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }
    }

    public NeoWSService getNeoWSService()
    {
        return neoWSService;
    }

    public List<NearEarthObject> getNearEarthObjectList()
    {
        return nearEarthObjects;
    }
}
