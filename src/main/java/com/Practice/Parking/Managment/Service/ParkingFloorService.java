package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CreateParkingFloorRequest;
import com.Practice.Parking.Managment.Dtos.GetParkingFloorResponse;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.ParkingFloor;
import com.Practice.Parking.Managment.Model.ParkingSlot;
import com.Practice.Parking.Managment.Model.SlotStatus;
import com.Practice.Parking.Managment.Repository.ParkingFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingFloorService {
    @Autowired
    ParkingFloorRepository parkingRepository;

    public long addFloor(ParkingFloor parkingFloor) {
        for (int i = 0; i < parkingFloor.getFloorCapacity(); i++) {
            ParkingSlot slot = new ParkingSlot();
            slot.setSlotNumber(i);
            slot.setStatus(SlotStatus.AVAILABLE);
            slot.setParkingFloor(parkingFloor);

            parkingFloor.getSlots().add(slot);
        }
        return this.parkingRepository.save(parkingFloor).getId();
    }

    public GetParkingFloorResponse updateParkingFloor(ParkingFloor parkingFloor, long Id) {
        ParkingFloor existParkingFloor = this.parkingRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Parking Floor not found"));
        ParkingFloor updatedParkingFloor = this.merge(existParkingFloor, parkingFloor);
        return GetParkingFloorResponse.builder().parkingFloor(updatedParkingFloor).build();
    }

    public List<ParkingFloor> getAvailableSlots() {
        List<ParkingFloor> parkingFloor = this.parkingRepository.findAll();
        List<ParkingFloor> parkAvailability = new ArrayList<>();
        for (ParkingFloor pf : parkingFloor) {
            if (pf.getAvailableCapacity() > 0) {
                parkAvailability.add(pf);
            }
        }
        return parkAvailability;
    }

    public List<ParkingFloor> findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(int value) {
        return this.parkingRepository.findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(value);
    }

    private ParkingFloor merge(ParkingFloor existParkingFloor, ParkingFloor updatedParkingFloor) {

        if (updatedParkingFloor.getFloorCapacity() > 0
                && existParkingFloor.getFloorCapacity() != updatedParkingFloor.getFloorCapacity()) {
            int diff = updatedParkingFloor.getFloorCapacity() - existParkingFloor.getFloorCapacity();
            existParkingFloor.setFloorCapacity(updatedParkingFloor.getFloorCapacity());
            existParkingFloor.setAvailableCapacity(existParkingFloor.getAvailableCapacity() + diff);
        }
        return existParkingFloor;
    }

    public List<GetParkingFloorResponse> getAllFloors() {
        return parkingRepository.findAll().stream()
                .map(floor -> GetParkingFloorResponse.builder().parkingFloor(floor).build())
                .collect(Collectors.toList());
    }
}
