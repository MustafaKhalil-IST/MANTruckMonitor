package org.man.truckmonitor.backend.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.man.truckmonitor.backend.dto.POISearchDTO;
import org.man.truckmonitor.backend.dto.TruckDTO;
import org.man.truckmonitor.backend.dto.TruckLocationDTO;
import org.man.truckmonitor.backend.exceptions.TruckException;
import org.man.truckmonitor.backend.service.PoIService;
import org.man.truckmonitor.backend.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class TruckController {
    @Autowired
    private TruckService truckService;

    @Autowired
    private PoIService poIService;

    @CrossOrigin
    @GetMapping("/api/trucks/{plate}")
    public TruckDTO getTruckByPlate(@PathVariable String plate) throws TruckException {
        return truckService.getTruckByPlate(plate);
    }

    @CrossOrigin
    @PostMapping("/api/trucks")
    public TruckDTO createTruck(@RequestBody TruckDTO truckDTO) throws TruckException {
        return truckService.createTruck(truckDTO);
    }

    @CrossOrigin
    @PostMapping("/api/pois")
    public JsonNode getPOIs(@RequestBody POISearchDTO poiSearchDTO) {
        return poIService.getPOIs(poiSearchDTO);
    }


    @CrossOrigin
    @PostMapping("/api/trucks/{plate}")
    public TruckLocationDTO updateTruckLocation(@PathVariable String plate, @Valid @RequestBody TruckLocationDTO truckLocationDTO) throws TruckException {
        return truckService.updateTruckLocation(truckLocationDTO, plate);
    }

}