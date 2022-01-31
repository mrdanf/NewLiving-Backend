package com.newliving.backend.dienstleistung.osm;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class RestService {

    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String[] getAdresseLonLat(String plz, String street) {
        String url = "https://nominatim.openstreetmap.org/search/" + plz + " " + street + "?format=json&limit=1";
        JSONArray response = restTemplate.getForObject(url, JSONArray.class);

        String lon = getLon(response);
        String lat = getLat(response);

        return new String[]{lon, lat};
    }

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
        ArrayList<LinkedHashMap<String, Object>> routes = (ArrayList<LinkedHashMap<String, Object>>) response.get("routes");
        LinkedHashMap<String, Object> routesAt0 = routes.get(0);
        return routesAt0.get("distance").toString();
    }

    private String getDuration(JSONObject response) {
        ArrayList<LinkedHashMap<String, Object>> routes = (ArrayList<LinkedHashMap<String, Object>>) response.get("routes");
        LinkedHashMap<String, Object> routesAt0 = routes.get(0);
        return routesAt0.get("duration").toString();
    }

    private String getLon(JSONArray response) {
        JSONObject object = new JSONObject((Map<String, ?>) response.get(0));
        return object.getAsString("lon");
    }

    private String getLat(JSONArray response) {
        JSONObject object = new JSONObject((Map<String, ?>) response.get(0));
        return object.getAsString("lat");
    }
}