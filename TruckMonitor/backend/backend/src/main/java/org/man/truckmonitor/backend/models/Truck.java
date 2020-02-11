package org.man.truckmonitor.backend.models;

import org.man.truckmonitor.backend.dto.TruckDTO;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "trucks")
public class Truck implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Column(length = 40, name = "plate", unique = true, nullable = false)
    private String licensePlate;

    @OneToMany(mappedBy = "truck", fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<TruckLocation> locationSet = new HashSet<TruckLocation>();

    public Set<TruckLocation> getLocationSet() {
        return locationSet;
    }

    public void setLocationSet(Set<TruckLocation> locationSet) {
        this.locationSet = locationSet;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void addLocation(TruckLocation truckLocation){
        this.locationSet.add(truckLocation);
    }
}