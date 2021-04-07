package dawid.kantoch.nasaapiphotos.Services;

import dawid.kantoch.nasaapiphotos.Interfaces.JsonOperations;
import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Category;
import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Event;
import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Geometries;
import dawid.kantoch.nasaapiphotos.Models.EONETpackage.Sources;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EONETService implements JsonOperations
{
    private Logger log = LoggerFactory.getLogger(EONETService.class);

    @Value("${apiKey}")
    private String apiKey;

    public EONETService()
    {

    }

    public List<Event> getData(int limit) throws IOException
    {
        List<Event> list = new ArrayList<>();
        JSONObject json = readJsonFromUrl("https://eonet.sci.gsfc.nasa.gov/api/v2.1/events?limit="+limit);
        JSONArray array = json.getJSONArray("events");
        for(int i=0;i<array.length();i++)
        {
            String id = array.getJSONObject(i).getString("id");
            String title = array.getJSONObject(i).getString("title");
            String link = array.getJSONObject(i).getString("link");
            Category category = new Category();
            JSONArray categoriesArray = array.getJSONObject(i).getJSONArray("categories");
            for(int j=0;j<categoriesArray.length();j++)
            {
                category.setId(categoriesArray.getJSONObject(j).getLong("id"));
                category.setTitle(categoriesArray.getJSONObject(j).getString("title"));
            }

            List<Sources> sourcesList = new ArrayList<>();
            JSONArray sourcesArray = array.getJSONObject(i).getJSONArray("sources");
            for(int j=0;j<sourcesArray.length();j++)
            {
                sourcesList.add(new Sources(sourcesArray.getJSONObject(j).getString("id"),sourcesArray.getJSONObject(j).getString("url")));
            }

            List<Geometries> geometriesList = new ArrayList<>();
            JSONArray geometriesArray = array.getJSONObject(i).getJSONArray("geometries");
            for(int j=0;j<geometriesArray.length();j++)
            {
                double coordinates[] = new double[2];
                String dateString = geometriesArray.getJSONObject(j).getString("date");
                LocalDateTime localdatetime = LocalDateTime.parse(dateString.substring(0,19));

                JSONArray coordinatesArray = geometriesArray.getJSONObject(j).getJSONArray("coordinates");
                for(int k=0;k<coordinatesArray.length();k++)
                {
                    coordinates[k] = coordinatesArray.getDouble(k);
                }
                geometriesList.add(new Geometries(localdatetime,
                        geometriesArray.getJSONObject(j).getString("type"),coordinates[0],coordinates[1]
                        ));
            }
            list.add(new Event(id,title,link,sourcesList,category,geometriesList));
        }
        log.info("Passing "+list.size()+" elements from events list to controller");
        return list;
    }
}
