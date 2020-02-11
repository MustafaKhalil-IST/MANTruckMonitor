package org.man.truckmonitor.backend.dto;

public class POISearchDTO {
    private String type;
    private Long distance;
    private Double lng;
    private Double lat;

    public POISearchDTO() {
    }

    public POISearchDTO(String type, Long distance, Double lng, Double lat) {
        this.type = type;
        this.distance = distance;
        this.lng = lng;
        this.lat = lat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
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
