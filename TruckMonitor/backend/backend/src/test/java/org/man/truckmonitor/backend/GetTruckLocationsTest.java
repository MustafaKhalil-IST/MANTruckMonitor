package org.man.truckmonitor.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.man.truckmonitor.backend.dto.TruckDTO;
import org.man.truckmonitor.backend.dto.TruckLocationDTO;
import org.man.truckmonitor.backend.exceptions.TruckException;
import org.man.truckmonitor.backend.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetTruckLocationsTest {
    @Autowired(required = true)
    private TruckService truckService;

    @Test
    public void success() throws TruckException {
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setLicensePlate("PT242344219124");
        truckService.createTruck(truckDTO);

        TruckDTO truckDTO1 = truckService.getTruckByPlate("PT242344219124");
        Assert.assertEquals(truckDTO1.getLocations().size(), 0);

        TruckLocationDTO truckLocationDTO = new TruckLocationDTO();
        truckLocationDTO.setLat(12.13452);
        truckLocationDTO.setLng(-38.12124);

        truckService.updateTruckLocation(truckLocationDTO, "PT242344219124");
        truckDTO1 = truckService.getTruckByPlate("PT242344219124");
        Assert.assertEquals(1, truckDTO1.getLocations().size());
        Assert.assertEquals(Double.valueOf(12.13452), truckDTO1.getLocations().get(0).getLat());
        Assert.assertEquals(Double.valueOf(-38.12124), truckDTO1.getLocations().get(0).getLng());
        Assert.assertNotNull(truckDTO1.getLocations().get(0).getTimestamp());

        truckService.deleteTruck("PT242344219124");
    }
}
