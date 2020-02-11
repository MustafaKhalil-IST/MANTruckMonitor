package org.man.truckmonitor.backend.repository;

import org.man.truckmonitor.backend.models.TruckLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TruckLocationRepository extends JpaRepository<TruckLocation, Long> {
}