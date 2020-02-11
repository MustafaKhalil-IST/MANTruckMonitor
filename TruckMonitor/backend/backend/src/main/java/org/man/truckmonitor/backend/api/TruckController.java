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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@Api(description = "Set of endpoints for Trucks and Points of interests")
public class TruckController {
    private static Logger logger = LoggerFactory.getLogger(TruckController.class);

    @Autowired
    private TruckService truckService;

    @Autowired
    private PoIService poIService;

    @CrossOrigin
    @GetMapping("/api/trucks/{plate}")
    @ApiOperation("get truck by plate license")
    public TruckDTO getTruckByPlate(@PathVariable String plate) throws TruckException {
        logger.info("getTruckByPlate: plate={}", plate);
        return truckService.getTruckByPlate(plate);
    }

    @CrossOrigin
    @PostMapping("/api/trucks")
    @ApiOperation("create truck")
    public TruckDTO createTruck(@RequestBody TruckDTO truckDTO) throws TruckException {
        logger.info("createTruck: plate={}", truckDTO.getLicensePlate());
        return truckService.createTruck(truckDTO);
    }

    @CrossOrigin
    @PostMapping("/api/pois")
    @ApiOperation("search points of interests")
    public JsonNode searchPOIs(@RequestBody POISearchDTO poiSearchDTO) {
        logger.info("searchPOIs: distance={}, type={}, lat={}, lng={}", poiSearchDTO.getDistance(),
                poiSearchDTO.getType(), poiSearchDTO.getLat(), poiSearchDTO.getLng());
        return poIService.getPOIs(poiSearchDTO);
    }


    @CrossOrigin
    @PostMapping("/api/trucks/{plate}")
    @ApiOperation("update truck location")
    public TruckLocationDTO updateTruckLocation(@PathVariable String plate, @Valid @RequestBody TruckLocationDTO truckLocationDTO)
            throws TruckException {
        logger.info("updateTruckLocation: plate={}, lat={}, lng={}", plate, truckLocationDTO.getLat(), truckLocationDTO.getLng());
        return truckService.updateTruckLocation(truckLocationDTO, plate);
    }

}