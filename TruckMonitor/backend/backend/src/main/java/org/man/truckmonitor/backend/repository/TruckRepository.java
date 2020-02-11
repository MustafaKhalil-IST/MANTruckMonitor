package org.man.truckmonitor.backend.repository;

        import org.man.truckmonitor.backend.models.Truck;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.stereotype.Repository;

@Repository
public interface TruckRepository extends JpaRepository<Truck, Long> {
}