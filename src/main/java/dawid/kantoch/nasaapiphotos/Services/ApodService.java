package dawid.kantoch.nasaapiphotos.Services;

import dawid.kantoch.nasaapiphotos.Interfaces.JsonOperations;
import dawid.kantoch.nasaapiphotos.Models.Apod;
import dawid.kantoch.nasaapiphotos.Repos.ApodRepo;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

@Service
public class ApodService implements JsonOperations
{
    private ApodRepo apodRepo;

    public ApodService(ApodRepo apodRepo)
    {
        this.apodRepo = apodRepo;
    }


    @Override
    public String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public JSONObject readJsonFromUrl(String url) throws IOException
    {
        try (InputStream is = new URL(url).openStream())
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void getData() throws IOException
    {
        JSONObject json = readJsonFromUrl("https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY");
        String date = json.getString("date");
        String explanation = json.getString("explanation");
        String hdurl = json.getString("hdurl");
        String title = json.getString("title");

        apodRepo.addApod(new Apod(date,explanation,hdurl,title));

        System.out.println("Dodano " + apodRepo.getApodList().size() + " zdjęcia dnia do listy!");
    }
}
