package dawid.kantoch.nasaapiphotos.Services;

import dawid.kantoch.nasaapiphotos.Interfaces.JsonOperations;
import dawid.kantoch.nasaapiphotos.Models.*;
import dawid.kantoch.nasaapiphotos.Repos.ApodRepo;
import dawid.kantoch.nasaapiphotos.Repos.NeoWSRepo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class NeoWSService implements JsonOperations
{
    private Logger log = LoggerFactory.getLogger(NeoWSService.class);

    @Value("${apiKey}")
    private String apiKey;

    public NeoWSService()
    {

    }

    List<String> listOfDaysToLookFor(NeoWSDate neoWSDate)
    {
        List<String> listOfDays = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(neoWSDate.getDateFrom());

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(neoWSDate.getDateTo());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        while (calendar.before(endCalendar))
        {
            String result = format.format(calendar.getTime());
            listOfDays.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        listOfDays.add(format.format(neoWSDate.getDateTo()));
        return listOfDays;
    }

    public List<NearEarthObject> getData(NeoWSDate neoWSDate) throws Exception
    {
        //https://api.nasa.gov/neo/rest/v1/feed?start_date=2015-09-07&end_date=2015-09-08&api_key=DEMO_KEY
        List<String> listOfDays = listOfDaysToLookFor(neoWSDate);
        if (listOfDays.size()==0 || listOfDays.size()>7)
        {
            throw new Exception("This request would not return any data or does not meet requirements!");
        }
        List<NearEarthObject> nearEarthObjects = new ArrayList<>();
        log.info("Downloading list of objects");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateFrom = format.format(neoWSDate.getDateFrom());
        String dateTo = format.format(neoWSDate.getDateTo());
        log.info("Looking for dates: "+dateFrom+" -> "+dateTo);
        JSONObject json = readJsonFromUrl("https://api.nasa.gov/neo/rest/v1/feed?start_date=" + dateFrom + "&end_date=" + dateTo + "&api_key=" + apiKey);
        JSONObject object = json.getJSONObject("near_earth_objects");

        for(int i=0;i<listOfDays.size();i++)
        {
            JSONArray arr = object.getJSONArray(String.valueOf(listOfDays.get(i)));
            if (arr.length() == 0)
            {
                log.warn("JSON for the date "+listOfDays.get(i)+" has no records!");
                continue;
            }
            else
            {
                for (int j = 0; j < arr.length(); j++)
                {
                    Long id = arr.getJSONObject(j).getLong("id");
                    Long neoReferenceId = arr.getJSONObject(j).getLong("neo_reference_id");
                    String name = arr.getJSONObject(j).getString("name");
                    String nasaJplUrl = arr.getJSONObject(j).getString("nasa_jpl_url");
                    double absoluteMagnitudeHeight = arr.getJSONObject(j).getDouble("absolute_magnitude_h");
                    JSONObject estimated_diameter_object = arr.getJSONObject(j).getJSONObject("estimated_diameter");
                    JSONObject meters = estimated_diameter_object.getJSONObject("meters");
                    double estimatedDiameterInMetersMin = meters.getDouble("estimated_diameter_min");
                    double estimatedDiameterInMetersMax = meters.getDouble("estimated_diameter_max");
                    boolean isPotentiallyHazardousAsteroid = arr.getJSONObject(j).getBoolean("is_potentially_hazardous_asteroid");
                    boolean isSentryObject = arr.getJSONObject(j).getBoolean("is_sentry_object");

                    JSONArray close_approach_data = arr.getJSONObject(j).getJSONArray("close_approach_data");
                    if (close_approach_data.length() == 0)
                    {
                        log.warn("JSON for the date "+listOfDays.get(j)+" has no records!");
                        continue;
                    }
                    else
                    {
                        LocalDate date = null;
                        Long epoch_date_close_approach =null;
                        double relative_velocity = 0;
                        double miss_distance = 0;
                        String orbiting_body = null;

                        for(int k=0;k<close_approach_data.length();k++)
                        {
                            String close_approach_date = close_approach_data.getJSONObject(k).getString("close_approach_date");
                            date = LocalDate.parse(close_approach_date);
                            epoch_date_close_approach = close_approach_data.getJSONObject(k).getLong("epoch_date_close_approach");

                            JSONObject relative_velocity_obj = close_approach_data.getJSONObject(k).getJSONObject("relative_velocity");
                            relative_velocity = relative_velocity_obj.getDouble("kilometers_per_hour");
                            JSONObject miss_distance_obj = close_approach_data.getJSONObject(k).getJSONObject("miss_distance");
                            miss_distance = miss_distance_obj.getDouble("kilometers");

                            orbiting_body = close_approach_data.getJSONObject(k).getString("orbiting_body");
                        }
                        CloseApproachData closeApproachData = new CloseApproachData(date,epoch_date_close_approach,relative_velocity,miss_distance,orbiting_body);
                        nearEarthObjects.add(new NearEarthObject(id,neoReferenceId,name,nasaJplUrl,absoluteMagnitudeHeight,estimatedDiameterInMetersMin,estimatedDiameterInMetersMax,isPotentiallyHazardousAsteroid,closeApproachData,isSentryObject));
                    }
                }
            }
        }
        log.info("Final list size: " + nearEarthObjects.size());
        return nearEarthObjects;
    }

}
