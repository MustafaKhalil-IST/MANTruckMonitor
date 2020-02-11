package org.man.truckmonitor.backend.dto;

import java.util.List;
import java.util.Set;

public class TruckDTO {
    private Long Id;
    private String licensePlate;
    private List<TruckLocationDTO> locations;

    public TruckDTO(){
    }

    public TruckDTO(Long id, String licensePlate, List<TruckLocationDTO> locations) {
        Id = id;
        this.licensePlate = licensePlate;
        this.locations = locations;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public List<TruckLocationDTO> getLocations() {
        return locations;
    }

    public void setLocations(List<TruckLocationDTO> locations) {
        this.locations = locations;
    }
}
