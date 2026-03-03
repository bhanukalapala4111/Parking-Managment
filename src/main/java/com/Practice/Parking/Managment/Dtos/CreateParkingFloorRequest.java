package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.ParkingFloor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateParkingFloorRequest {

    Integer floorNumber;

    Integer floorCapacity;

    Integer availableCapacity;

    public ParkingFloor toParkingFloor() {
        return ParkingFloor.builder().floorNumber(floorNumber).floorCapacity(floorCapacity)
                .availableCapacity(availableCapacity).build();
    }
}
