package org.man.truckmonitor.backend.service;

import org.man.truckmonitor.backend.dto.TruckDTO;
import org.man.truckmonitor.backend.dto.TruckLocationDTO;
import org.man.truckmonitor.backend.exceptions.TruckException;
import org.man.truckmonitor.backend.models.Truck;
import org.man.truckmonitor.backend.models.TruckLocation;
import org.man.truckmonitor.backend.repository.TruckLocationRepository;
import org.man.truckmonitor.backend.repository.TruckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TruckService {
    @Autowired
    private TruckLocationRepository truckLocationRepository;

    @Autowired
    private TruckRepository truckRepository;

    public TruckDTO getTruckByPlate(String plate) throws TruckException {
        Optional<Truck> truck = truckRepository.findAll().stream().filter(t -> t.getLicensePlate().equals(plate)).findFirst();
        if(truck.isPresent()) {
            List<TruckLocationDTO> locations = truck.get().getLocationSet().stream().map(l -> new TruckLocationDTO(l.getCreatedAt(), l.getLng(), l.getLat())).collect(Collectors.toList());
            locations.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));
            if (locations.size() > 20) {
                locations = locations.subList(locations.size() - 20, locations.size());
            }
            return new TruckDTO(truck.get().getId(), truck.get().getLicensePlate(), locations);
        }
        else {
            throw new TruckException("Truck not found");
        }
    }

    public TruckDTO createTruck(TruckDTO truckDTO) throws TruckException{
        if (truckDTO.getLicensePlate().equals("") ||truckDTO.getLicensePlate() == null) {
            throw new TruckException("Plate is missing");
        }
        Truck truck = new Truck();
        truck.setLicensePlate(truckDTO.getLicensePlate());
        truck.setCreatedAt(new Date());
        truckRepository.save(truck);
        return truckDTO;
    }

    public void deleteTruck(String plate) {
        Optional<Truck> truck = truckRepository.findAll().stream().filter(t -> t.getLicensePlate().equals(plate)).findFirst();
        truck.ifPresent(value -> truckRepository.delete(value));
    }

    public TruckLocationDTO updateTruckLocation(TruckLocationDTO truckLocationDTO, String plate) throws TruckException {
        Optional<Truck> truck = truckRepository.findAll().stream().filter(t -> t.getLicensePlate().equals(plate)).findFirst();
        if(truck.isPresent()) {
            TruckLocation truckLocation = new TruckLocation();
            truckLocation.setTruck(truck.get());
            truckLocation.setLat(truckLocationDTO.getLat());
            truckLocation.setLng(truckLocationDTO.getLng());
            truckLocation.setCreatedAt(new Date());
            truckLocationRepository.save(truckLocation);
            truck.get().addLocation(truckLocation);
            truckRepository.save(truck.get());
            return truckLocationDTO;
        }
        else {
            throw new TruckException("Truck not found");
        }
    }
}
