package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.ParkingFloor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetParkingFloorResponse {
    ParkingFloor parkingFloor;
}
