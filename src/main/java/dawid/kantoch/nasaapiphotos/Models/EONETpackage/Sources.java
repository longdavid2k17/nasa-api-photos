package dawid.kantoch.nasaapiphotos.Models.EONETpackage;

import lombok.Data;

@Data
public class Sources
{
    private String id;
    private String url;

    public Sources(String id, String url)
    {
        this.id = id;
        this.url = url;
    }

    public String getId()
    {
        return id;
    }

    public String getUrl()
    {
        return url;
    }
}
