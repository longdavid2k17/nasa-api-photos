package dawid.kantoch.nasaapiphotos.Models;

import lombok.Data;

@Data
public class APOD
{
    String date;
    String explanation;
    String hdurl;
    String title;

    public APOD(String date, String explanation, String hdurl, String title)
    {
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.title = title;
    }
}
