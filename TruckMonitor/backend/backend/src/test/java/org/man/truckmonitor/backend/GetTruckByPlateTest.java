package org.man.truckmonitor.backend;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.man.truckmonitor.backend.dto.TruckDTO;
import org.man.truckmonitor.backend.exceptions.TruckException;
import org.man.truckmonitor.backend.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetTruckByPlateTest {
    @Autowired(required = true)
    private TruckService truckService;

    @Test
    public void success() throws TruckException{
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setLicensePlate("PT2423442912");
        truckService.createTruck(truckDTO);

        TruckDTO truck = truckService.getTruckByPlate("PT2423442912");
        Assert.assertNotNull(truck);
        Assert.assertEquals("PT2423442912", truck.getLicensePlate());

        truckService.deleteTruck("PT2423442912");
    }

    @Test(expected = TruckException.class)
    public void EmptyLicensePlate() throws TruckException{
        truckService.getTruckByPlate("NOT_EXISTENT_CAR");
    }
}
