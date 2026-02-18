package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CreateParkingFloorRequest;
import com.Practice.Parking.Managment.Dtos.GetParkingFloorResponse;
import com.Practice.Parking.Managment.Dtos.UpdateParkingFloorRequest;
import com.Practice.Parking.Managment.Model.ParkingFloor;
import com.Practice.Parking.Managment.Service.ParkingFloorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("parking")
public class ParkingFloorController {
    @Autowired
    ParkingFloorService parkingService;

    @PostMapping("addFloor")
    public long AddFloor(@RequestBody CreateParkingFloorRequest addFloor) {
        return this.parkingService.addFloor(addFloor.toParkingFloor());
    }

    @GetMapping("getAvailableSlots")
    @ResponseBody
    public List<ParkingFloor> getAvailableSlots() {
        return parkingService.getAvailableSlots();
    }

    // update floor
    @PatchMapping("update/{id}")
    public GetParkingFloorResponse updateParkingFloor(@RequestBody UpdateParkingFloorRequest updateParkingFloorRequest,
            @PathVariable long id) {
        return this.parkingService.updateParkingFloor(updateParkingFloorRequest.toParkingFloor(), id);
    }

    @GetMapping("/floors")
    public List<GetParkingFloorResponse> getAllFloors() {
        return this.parkingService.getAllFloors();
    }

}
