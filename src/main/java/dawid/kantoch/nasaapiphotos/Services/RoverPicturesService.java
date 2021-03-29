package dawid.kantoch.nasaapiphotos.Services;

import dawid.kantoch.nasaapiphotos.Interfaces.JsonOperations;
import dawid.kantoch.nasaapiphotos.Models.Camera;
import dawid.kantoch.nasaapiphotos.Models.Photos;
import dawid.kantoch.nasaapiphotos.Models.Rover;
import dawid.kantoch.nasaapiphotos.Repos.DataRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;

@Service
public class RoverPicturesService implements JsonOperations
{
    private Logger log = LoggerFactory.getLogger(RoverPicturesService.class);

    private DataRepo dataRepo;

    @Value("${apiKey}")
    private String apiKey;

    public RoverPicturesService(DataRepo dataRepo)
    {
        this.dataRepo = dataRepo;
    }


    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void getData() throws IOException
    {
        //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY
        log.info("Downloading list of Rover pictures");
        JSONObject json = readJsonFromUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key="+apiKey);
        JSONArray arr = json.getJSONArray("photos");
        if(arr.length()==0)
        {
            log.warn("JSON has no records!");
        }
        else
        {
            for (int i = 0; i < arr.length(); i++)
            {
                Long id = arr.getJSONObject(i).getLong("id");
                int sol = arr.getJSONObject(i).getInt("sol");

                JSONObject cameraObject = arr.getJSONObject(i).getJSONObject("camera");
                Long cameraId = cameraObject.getLong("id");
                String cameraName = cameraObject.getString("name");
                String cameraFullName = cameraObject.getString("full_name");
                int camera_roverId = cameraObject.getInt("rover_id");

                String img_src = arr.getJSONObject(i).getString("img_src");
                String earth_date = arr.getJSONObject(i).getString("earth_date");
                LocalDate date = LocalDate.parse(earth_date);

                JSONObject roverObject = arr.getJSONObject(i).getJSONObject("rover");
                Long roverId = roverObject.getLong("id");
                String roverName = roverObject.getString("name");
                String roverLanding_date = roverObject.getString("landing_date");
                String roverLaunch_date = roverObject.getString("launch_date");
                String roverStatus = roverObject.getString("status");

                dataRepo.addPhoto(new Photos(
                        id,
                        sol,
                        new Camera(cameraId,cameraName,cameraFullName,camera_roverId),
                        img_src,
                        date.toString(),
                        new Rover(roverId,roverName,roverLanding_date,roverLaunch_date,roverStatus)));
            }
            log.info("Final list size: "+dataRepo.getPhotosList().size());
        }
    }
}
