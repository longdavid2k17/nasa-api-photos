package dawid.kantoch.nasaapiphotos.Services;

import dawid.kantoch.nasaapiphotos.Models.Camera;
import dawid.kantoch.nasaapiphotos.Models.Photos;
import dawid.kantoch.nasaapiphotos.Models.Rover;
import dawid.kantoch.nasaapiphotos.Repos.DataRepo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;

@Service
public class NasaAPI
{
    private DataRepo dataRepo;

    public NasaAPI(DataRepo dataRepo)
    {
        this.dataRepo = dataRepo;
    }

    private static String readAll(Reader rd) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1)
        {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
    {
        InputStream is = new URL(url).openStream();
        try
        {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        }
        finally
        {
            is.close();
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void getData() throws IOException
    {
        //https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY
        JSONObject json = readJsonFromUrl("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY");
        JSONArray arr = json.getJSONArray("photos");
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
        System.out.println("Uzyskana lista zawiera "+dataRepo.getPhotosList().size());
    }
}
