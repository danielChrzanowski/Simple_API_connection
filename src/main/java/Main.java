import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.google.gson.*;
import com.google.gson.reflect.*;

public class Main {
    public static Map<String, Object> jsonToMap(String str) {
        Map<String, Object> map = new Gson().fromJson(
                str, new TypeToken<HashMap<String, Object>>() {
                }.getType()
        );
        return map;
    }

    public static void main(String[] args) {
        String API_KEY = "d01fb321aa86de72d5d295c47e1082b6";
        String LOCATION = "Katowice";
        String URLString = "http://api.openweathermap.org/data/2.5/weather?q="+LOCATION+"&appid="+API_KEY;

        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(URLString);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();
            System.out.println("Result: "+result.toString());

            Map<String, Object> respMap = jsonToMap(result.toString());
            Map<String, Object> mainMap = jsonToMap(respMap.get("main").toString());
            Map<String, Object> windMap = jsonToMap(respMap.get("wind").toString());

            System.out.println("Temp: " + mainMap.get("temp"));
            System.out.println("Humidity: " + mainMap.get("humidity"));
            System.out.println("Wind speed: " + windMap.get("speed"));
            System.out.println("Wind angle: " + windMap.get("deg"));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
