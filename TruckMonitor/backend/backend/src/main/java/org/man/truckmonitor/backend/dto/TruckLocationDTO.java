package org.man.truckmonitor.backend.dto;

import java.util.Date;
import java.sql.Timestamp;

public class TruckLocationDTO {
    private Date timestamp;
    private Double lng;
    private Double lat;

    public TruckLocationDTO() {
    }

    public TruckLocationDTO(Date timestamp, Double lng, Double lat) {
        this.timestamp = timestamp;
        this.lng = lng;
        this.lat = lat;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }


}
