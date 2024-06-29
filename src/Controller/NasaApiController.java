package Controller;

import Model.MarsPhoto;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NasaApiController {
    private static final String API_KEY = "CHuo7RDjWZKzOhOLJ6yYakZjzyOo7Npugjt4ELm6";
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/";

    public List<MarsPhoto> getPhotos(String rover, int sol, String camera, LocalDate startDate, LocalDate endDate) throws Exception {
        List<MarsPhoto> allPhotos = new ArrayList<>();
        int page = 1;
        boolean hasMorePhotos = true;

        while (hasMorePhotos) {
            String urlString = BASE_URL + rover + "/photos?sol=" + sol + "&camera=" + camera +
                    "&earth_date=" + startDate + "&end_date=" + endDate + "&page=" + page + "&api_key=" + API_KEY;
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("Error al obtener las fotos: " + responseCode);
            }

            JSONObject json = new JSONObject(getResponseContent(conn));
            JSONArray jsonArray = json.getJSONArray("photos");

            if (jsonArray.length() == 0) {
                hasMorePhotos = false; // No more photos on the next page
            } else {
                allPhotos.addAll(parsePhotos(jsonArray));
                page++;
            }
        }

        return allPhotos;
    }

    private List<MarsPhoto> parsePhotos(JSONArray jsonArray) {
        List<MarsPhoto> photos = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject photoJson = jsonArray.getJSONObject(i);
            JSONObject cameraJson = photoJson.getJSONObject("camera");
            JSONObject roverJson = photoJson.getJSONObject("rover");

            // Extract the list of cameras
            JSONArray camerasArray = roverJson.getJSONArray("cameras");
            List<String> camerasList = new ArrayList<>();
            for (int j = 0; j < camerasArray.length(); j++) {
                camerasList.add(camerasArray.getJSONObject(j).getString("full_name"));
            }

            photos.add(new MarsPhoto(
                    photoJson.getInt("id"),
                    photoJson.getInt("sol"),
                    cameraJson.getString("name"),
                    cameraJson.getString("full_name"),
                    photoJson.getString("img_src"),
                    LocalDate.parse(photoJson.getString("earth_date")),
                    roverJson.getString("name"),
                    roverJson.getString("status"),
                    LocalDate.parse(roverJson.getString("landing_date")),
                    LocalDate.parse(roverJson.getString("launch_date")),
                    roverJson.getInt("max_sol"),
                    LocalDate.parse(roverJson.getString("max_date")),
                    roverJson.getInt("total_photos"),
                    camerasList
            ));
        }

        return photos;
    }

    private String getResponseContent(HttpURLConnection conn) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return content.toString();
    }
}