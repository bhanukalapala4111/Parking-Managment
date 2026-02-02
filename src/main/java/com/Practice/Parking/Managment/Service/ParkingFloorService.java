package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CreateParkingFloorRequest;
import com.Practice.Parking.Managment.Model.ParkingFloor;
import com.Practice.Parking.Managment.Model.ParkingSlot;
import com.Practice.Parking.Managment.Model.SlotStatus;
import com.Practice.Parking.Managment.Repository.ParkingFloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingFloorService {
    @Autowired
    ParkingFloorRepository parkingRepository;

    public long addFloor(CreateParkingFloorRequest addFloorRequest){
        ParkingFloor parkingFloor= ParkingFloor.builder().floorNumber(addFloorRequest.getFloorNumber()).floorCapacity(addFloorRequest.getFloorCapacity()).availableCapacity(addFloorRequest.getAvailableCapacity()).build();
        for (int i = 0; i < addFloorRequest.getFloorCapacity();i++){
            ParkingSlot slot = new ParkingSlot();
            slot.setSlotNumber(i);
            slot.setStatus(SlotStatus.AVAILABLE);
            slot.setParkingFloor(parkingFloor);

            parkingFloor.getSlots().add(slot);
        }
        return this.parkingRepository.save(parkingFloor).getId();
    }
    public List<ParkingFloor> getAvailableSlots(){
        List<ParkingFloor> parkingFloor= this.parkingRepository.findAll();
        List<ParkingFloor> parkAvailability = new ArrayList<>();
        for(ParkingFloor pf: parkingFloor){
            if(pf.getAvailableCapacity()>0){
                parkAvailability.add(pf);
            }
        }
        return parkAvailability;
    }

    public List<ParkingFloor> findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(int value){
        return this.parkingRepository.findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(value);
    }
}
