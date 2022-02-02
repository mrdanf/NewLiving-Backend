package com.newliving.backend.dienstleistung.osm;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Stellt die Verbindung zu OpenStreetMap her und holt die Koordinaten und Routinginformationen.
 */
@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /**
     * Holt Längen- (Longitude) und Breitengrad (Latitude) der angefragten Adresse.
     *
     * @param plz    PLZ der Stadt
     * @param street Straße
     * @return String[] mit String[0] = longitude und String[1] = latitude
     */
    public String[] getAdresseLonLat(String plz, String street) {
        String url = "https://nominatim.openstreetmap.org/search/" + plz + " " + street + "?format=json&limit=1";
        JSONArray response = restTemplate.getForObject(url, JSONArray.class);

        if (response == null) {
            throw new IllegalStateException("Adresse existiert nicht!");
        }

        String lon = getLon(response);
        String lat = getLat(response);

        return new String[]{lon, lat};
    }

    /**
     * Berechnet die optimale Route zwischen zwei Koordinatenpunkten. Liefert die Distanz und die Dauer zurück.
     *
     * @param altLonLat Startpunkt: erwartet String[0] = longitude und String[1] = latitude
     * @param neuLonLat Endpunkt: erwartet String[0] = longitude und String[1] = latitude
     * @return String[] mit String[0] = distance in Metern und String[1] = duration in Sekunden
     */
    public String[] getRoutingInfo(String[] altLonLat, String[] neuLonLat) {
        String url = "https://routing.openstreetmap.de/routed-car/route/v1/driving/" +
                altLonLat[0] + "," + altLonLat[1] + ";" +
                neuLonLat[0] + "," + neuLonLat[1] +
                "?alternatives=false";
        JSONObject response = restTemplate.getForObject(url, JSONObject.class);

        String distance = getDistance(response);
        String duration = getDuration(response);

        return new String[]{distance, duration};
    }

    private String getDistance(JSONObject response) {
        try {
            ArrayList<LinkedHashMap<String, Object>> routes = (ArrayList<LinkedHashMap<String, Object>>) response.get("routes");
            LinkedHashMap<String, Object> routesAt0 = routes.get(0);
            return routesAt0.get("distance").toString();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Adresse existiert nicht!");
        }
    }

    private String getDuration(JSONObject response) {
        try {
            ArrayList<LinkedHashMap<String, Object>> routes = (ArrayList<LinkedHashMap<String, Object>>) response.get("routes");
            LinkedHashMap<String, Object> routesAt0 = routes.get(0);
            return routesAt0.get("duration").toString();
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Adresse existiert nicht!");
        }
    }

    private String getLon(JSONArray response) {
        try {
            JSONObject object = new JSONObject((Map<String, ?>) response.get(0));
            return object.getAsString("lon");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Adresse existiert nicht!");
        }
    }

    private String getLat(JSONArray response) {
        try {
            JSONObject object = new JSONObject((Map<String, ?>) response.get(0));
            return object.getAsString("lat");
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalStateException("Adresse existiert nicht!");
        }
    }
}