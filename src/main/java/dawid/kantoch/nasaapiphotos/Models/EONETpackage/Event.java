package dawid.kantoch.nasaapiphotos.Models.EONETpackage;

import lombok.Data;

import java.util.List;

@Data
public class Event
{
    private String id;
    private String title;
    private String link;
    private List<Sources> sources;
    private Category category;
    private List<Geometries> geometries;

    public Event(String id, String title, String link, List<Sources> sources, Category category, List<Geometries> geometries)
    {
        this.id = id;
        this.title = title;
        this.link = link;
        this.sources = sources;
        this.category = category;
        this.geometries = geometries;
    }

    public Event()
    {

    }

    public List<Sources> getSources()
    {
        return sources;
    }

    public List<Geometries> getGeometries()
    {
        return geometries;
    }
}
