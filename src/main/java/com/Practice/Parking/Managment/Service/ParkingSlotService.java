package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.UserResponse;
import com.Practice.Parking.Managment.Model.ParkingFloor;
import com.Practice.Parking.Managment.Model.ParkingSlot;
import com.Practice.Parking.Managment.Model.SlotStatus;
import com.Practice.Parking.Managment.Repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    ParkingFloorService parkingFloorService;
    @Autowired
    ParkingSlotRepository parkingSlot;

    @Autowired
    UserService userService;


    public long getSlot(long id) {
        UserResponse userResponse=this.userService.getUser(id);

         if(true) {
             List<ParkingFloor> parkFloorList = this.parkingFloorService.findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(0);
             ParkingFloor parkingFloor = parkFloorList.get(0);
             List<ParkingSlot> park = parkingSlot.findByParkingFloorFloorNumberAndStatusOrderBySlotNumberAsc(parkingFloor.getFloorNumber(), SlotStatus.AVAILABLE);
             ParkingSlot parkingSlot1 = park.get(0);
             parkingSlot1.setStatus(SlotStatus.OCCUPIED);
         }
         return 0;
    }
}
