package dawid.kantoch.nasaapiphotos.Repos;

import dawid.kantoch.nasaapiphotos.Models.APOD;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class APODRepo
{
    private List<APOD> APODList;

    public APODRepo()
    {
        this.APODList = new ArrayList<>();
    }

    public List<APOD> getApodList()
    {
        return APODList;
    }

    public void addApod(APOD apod)
    {
        this.APODList.add(apod);
    }
}
