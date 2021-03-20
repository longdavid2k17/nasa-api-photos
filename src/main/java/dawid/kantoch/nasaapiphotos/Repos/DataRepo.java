package dawid.kantoch.nasaapiphotos.Repos;

import dawid.kantoch.nasaapiphotos.Models.Photos;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataRepo
{
    private List<Photos> photosList;

    public DataRepo(List<Photos> photosList)
    {
        this.photosList = photosList;
    }

    public List<Photos> getPhotosList()
    {
        return photosList;
    }

    public void addPhoto(Photos photo)
    {
        this.photosList.add(photo);
    }
}
