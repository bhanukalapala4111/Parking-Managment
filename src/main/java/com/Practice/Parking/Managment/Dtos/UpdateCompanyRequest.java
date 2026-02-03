package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Company;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UpdateCompanyRequest {

    String companyName;

    int totalCapacity;

    int availableCapacity;

    public Company toCompany(){
        return Company.builder().companyName(companyName).totalCapacity(totalCapacity).availableCapacity(availableCapacity).build();
    }
}
