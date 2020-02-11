package org.man.truckmonitor.backend;

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
public class CreateTruckTest {
    @Autowired(required = true)
    private TruckService truckService;

    @Test
    public void success() throws TruckException{
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setLicensePlate("PT242344271");
        truckService.createTruck(truckDTO);
        truckService.deleteTruck("PT242344271");
    }

    @Test(expected = TruckException.class)
    public void EmptyLicensePlate() throws TruckException{
        TruckDTO truckDTO = new TruckDTO();
        truckDTO.setLicensePlate("");
        truckService.createTruck(truckDTO);
    }
}
