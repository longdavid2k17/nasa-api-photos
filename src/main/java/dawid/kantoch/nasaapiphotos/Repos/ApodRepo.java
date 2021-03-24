package dawid.kantoch.nasaapiphotos.Repos;

import dawid.kantoch.nasaapiphotos.Models.Apod;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApodRepo
{
    private List<Apod> apodList;

    public ApodRepo(List<Apod> apodList)
    {
        this.apodList = apodList;
    }

    public List<Apod> getApodList()
    {
        return apodList;
    }

    public void addApod(Apod apod)
    {
        this.apodList.add(apod);
    }
}
