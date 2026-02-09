package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Company;
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

    Integer totalCapacity;

    public Company toComapny(){
        return Company.builder().companyName(companyName).totalCapacity(totalCapacity).availableCapacity(totalCapacity).build();
    }

}
