package org.man.truckmonitor.backend.service;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.man.truckmonitor.backend.dto.POISearchDTO;
import org.man.truckmonitor.backend.exceptions.PoIException;
import org.springframework.stereotype.Service;

@Service
public class PoIService {
    private final String APIKey = "AIzaSyDOS8jz32dPSNYqJWyehISYmERu85puNSQ";

    public String getSearchURL(POISearchDTO poiSearchDTO) throws PoIException{
        if (poiSearchDTO.getLng() == null ||poiSearchDTO.getLat() == null) {
            throw new PoIException("Lng or Lat are lost");
        }
        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
        url += "type=" + ((poiSearchDTO.getType() == null) ? "restaurant+gasstation+hotel": poiSearchDTO.getType());
        url += "&radius=" + ((poiSearchDTO.getDistance() == null) ? "1000": poiSearchDTO.getDistance());
        url += "&location=" + poiSearchDTO.getLat() + "," + poiSearchDTO.getLng();
        url += "&key=" + this.APIKey;
        System.out.println(url);
        return url;
    }

    public JsonNode getPOIs(POISearchDTO poiSearchDTO) {
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get(this.getSearchURL(poiSearchDTO)).asString();
            ObjectMapper mapper = new ObjectMapper();
            JsonFactory factory = mapper.getFactory();
            JsonParser parser = factory.createParser(response.getBody());
            return mapper.readTree(parser);
        }
        catch (Exception e) {
            return null;
        }
    }

}
