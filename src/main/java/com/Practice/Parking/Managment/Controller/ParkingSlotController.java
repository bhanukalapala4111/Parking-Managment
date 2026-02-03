package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("slot")
public class ParkingSlotController {
    @Autowired
    ParkingSlotService parkingSlotService;

    @PostMapping("/book/{Id}")
    public long getSlot(@PathVariable long Id){
        return this.parkingSlotService.getSlot(Id);
    }

    // Release slot
    @PatchMapping("update/{Id}")
    public boolean releaseSlot(@PathVariable long Id){
        return this.parkingSlotService.releaseSlot(Id);
    }
}
