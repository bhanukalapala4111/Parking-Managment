package com.Practice.Service;

import com.Practice.Dtos.CreateParkingFloorRequest;
import com.Practice.Model.ParkingFloor;
import com.Practice.Repository.ParkingFloorRepository;
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
}
