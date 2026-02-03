package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.ParkingFloor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateParkingFloorRequest {

    int floorNumber;

    int floorCapacity;

    public ParkingFloor toParkingFloor(){
        return ParkingFloor.builder().floorNumber(floorNumber).floorCapacity(floorCapacity).availableCapacity(floorCapacity).build();
    }
}
