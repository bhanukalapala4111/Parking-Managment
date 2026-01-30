package com.Practice.Controller;

import com.Practice.Service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("slot")
public class ParkingSlotController {
    @Autowired
    ParkingSlotService parkingSlotService;

    @GetMapping("/book/{Id}")
    long getSlot(@PathVariable long Id){
     return this.parkingSlotService.getSlot(Id);
    }


}
