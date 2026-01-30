package com.Practice.Controller;

import com.Practice.Dtos.CreateParkingFloorRequest;
import com.Practice.Model.ParkingFloor;
import com.Practice.Service.ParkingFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parking")
public class ParkingFloorController {
    @Autowired
    ParkingFloorService parkingService;

    @PostMapping("addFloor")
    long AddFloor(@RequestBody CreateParkingFloorRequest addFloor){
        return this.parkingService.addFloor(addFloor);
    }

    @GetMapping("getAvailableSlots")
    @ResponseBody
    List<ParkingFloor> getAvailableSlots(){
        return parkingService.getAvailableSlots();
    }



}
