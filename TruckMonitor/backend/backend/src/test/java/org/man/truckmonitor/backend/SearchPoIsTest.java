package org.man.truckmonitor.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.man.truckmonitor.backend.dto.POISearchDTO;
import org.man.truckmonitor.backend.dto.TruckDTO;
import org.man.truckmonitor.backend.exceptions.PoIException;
import org.man.truckmonitor.backend.service.PoIService;
import org.man.truckmonitor.backend.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchPoIsTest {
    @Autowired(required = true)
    private PoIService poIService;

    @Test
    public void success() throws PoIException {
        POISearchDTO poiSearchDTO = new POISearchDTO();
        poiSearchDTO.setDistance((long) 1000);
        poiSearchDTO.setLat(1.242231);
        poiSearchDTO.setLng(9.224234);
        poiSearchDTO.setType("restaurant");

        String url = poIService.getSearchURL(poiSearchDTO);
        Assert.assertEquals("https://maps.googleapis.com/maps/api/place/nearbysearch/json?type=restaurant" +
                "&radius=1000&location=1.242231,9.224234&key=AIzaSyDOS8jz32dPSNYqJWyehISYmERu85puNSQ", url);
    }

    @Test(expected = PoIException.class)
    public void LngMissed() throws PoIException {
        POISearchDTO poiSearchDTO = new POISearchDTO();
        poiSearchDTO.setDistance((long) 1000);
        poiSearchDTO.setLat(1.242231);
        poiSearchDTO.setType("restaurant");
        String url = poIService.getSearchURL(poiSearchDTO);
    }

    @Test(expected = PoIException.class)
    public void LatMissed() throws PoIException {
        POISearchDTO poiSearchDTO = new POISearchDTO();
        poiSearchDTO.setDistance((long) 1000);
        poiSearchDTO.setLng(1.242231);
        poiSearchDTO.setType("restaurant");
        String url = poIService.getSearchURL(poiSearchDTO);
    }
}
