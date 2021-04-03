package dawid.kantoch.nasaapiphotos.Services;

import dawid.kantoch.nasaapiphotos.Interfaces.JsonOperations;
import dawid.kantoch.nasaapiphotos.Models.Apod;
import dawid.kantoch.nasaapiphotos.Repos.ApodRepo;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ApodService implements JsonOperations
{
    private Logger log = LoggerFactory.getLogger(ApodService.class);

    private ApodRepo apodRepo;

    @Value("${apiKey}")
    private String apiKey;

    public ApodService(ApodRepo apodRepo)
    {
        this.apodRepo = apodRepo;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getData() throws IOException
    {
        log.info("Downloading POD from server");
        JSONObject json = readJsonFromUrl("https://api.nasa.gov/planetary/apod?api_key="+apiKey);
        String date = json.getString("date");
        String explanation = json.getString("explanation");
        String hdurl = json.getString("hdurl");
        String title = json.getString("title");

        apodRepo.addApod(new Apod(date,explanation,hdurl,title));

        log.info("Added " + apodRepo.getApodList().size() + " POD to the list");
    }
}
