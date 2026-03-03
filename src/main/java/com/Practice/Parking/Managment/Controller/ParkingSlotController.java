package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Model.ParkingSlot;
import com.Practice.Parking.Managment.Service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("slot")
public class ParkingSlotController {
    @Autowired
    ParkingSlotService parkingSlotService;

    @PostMapping("/book/{Id}")
    public long getSlot(@PathVariable("Id") long Id) {
        return this.parkingSlotService.getSlot(Id);
    }

    // Release slot
    @PatchMapping("update/{Id}")
    public boolean releaseSlot(@PathVariable("Id") long Id) {
        return this.parkingSlotService.releaseSlot(Id);
    }

    @GetMapping("/user/{userId}")
    public List<ParkingSlot> getUserBookings(@PathVariable("userId") long userId) {
        return this.parkingSlotService.getUserBookings(userId);
    }
}
