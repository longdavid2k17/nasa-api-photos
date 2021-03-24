package dawid.kantoch.nasaapiphotos.Interfaces;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;

public interface JsonOperations
{
    String readAll(Reader rd) throws IOException;
    JSONObject readJsonFromUrl(String url) throws IOException;
    void getData() throws IOException;
}
