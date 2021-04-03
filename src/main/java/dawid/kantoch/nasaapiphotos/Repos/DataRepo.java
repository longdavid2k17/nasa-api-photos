package dawid.kantoch.nasaapiphotos.Repos;

import dawid.kantoch.nasaapiphotos.Models.Photos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DataRepo
{
    private Logger log = LoggerFactory.getLogger(DataRepo.class);

    private List<Photos> photosList;

    public DataRepo()
    {
        this.photosList = new ArrayList<>();
    }

    public List<Photos> getPhotosList()
    {
        return photosList;
    }

    public void addPhoto(Photos photo)
    {
        this.photosList.add(photo);
    }

    public List<Photos> sortByCameraId(Long cameraId) throws Exception
    {
        List<Photos> filteredList = new ArrayList<>();
        for(Photos photo : photosList)
        {
            if(photo.getCamera().getId()==cameraId)
            {
                filteredList.add(photo);
            }
        }
        if(filteredList.size()==0)
        {
            throw new Exception("No such camera in primary list!");
        }
        else
            return filteredList;
    }

    public List<Photos> getPhotosBySol(int sol)
    {
        log.info("Getting new list by solar day (id="+sol+")");
        List<Photos> solPhotosList = new ArrayList<>();

        return solPhotosList;
    }
}
