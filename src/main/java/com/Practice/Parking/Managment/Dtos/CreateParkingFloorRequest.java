package com.Practice.Parking.Managment.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateParkingFloorRequest {

    int floorNumber;

    int floorCapacity;

    int availableCapacity;
}
