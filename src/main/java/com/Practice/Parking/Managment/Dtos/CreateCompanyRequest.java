package com.Practice.Parking.Managment.Dtos;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CreateCompanyRequest {

    String companyName;

    int totalCapacity;

}
