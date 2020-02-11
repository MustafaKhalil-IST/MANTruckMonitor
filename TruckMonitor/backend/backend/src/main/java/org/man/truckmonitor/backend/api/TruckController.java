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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api(description = "Set of endpoints for Trucks and Points of interests")
public class TruckController {
    @Autowired
    private TruckService truckService;

    @Autowired
    private PoIService poIService;

    @CrossOrigin
    @GetMapping("/api/trucks/{plate}")
    @ApiOperation("get truck by plate license")
    public TruckDTO getTruckByPlate(@PathVariable String plate) throws TruckException {
        return truckService.getTruckByPlate(plate);
    }

    @CrossOrigin
    @PostMapping("/api/trucks")
    @ApiOperation("create truck")
    public TruckDTO createTruck(@RequestBody TruckDTO truckDTO) throws TruckException {
        return truckService.createTruck(truckDTO);
    }

    @CrossOrigin
    @PostMapping("/api/pois")
    @ApiOperation("search points of interests")
    public JsonNode searchPOIs(@RequestBody POISearchDTO poiSearchDTO) {
        return poIService.getPOIs(poiSearchDTO);
    }


    @CrossOrigin
    @PostMapping("/api/trucks/{plate}")
    @ApiOperation("update truck location")
    public TruckLocationDTO updateTruckLocation(@PathVariable String plate, @Valid @RequestBody TruckLocationDTO truckLocationDTO) throws TruckException {
        return truckService.updateTruckLocation(truckLocationDTO, plate);
    }

}