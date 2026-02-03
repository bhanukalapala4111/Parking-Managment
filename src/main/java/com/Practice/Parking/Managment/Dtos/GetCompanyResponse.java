package com.Practice.Parking.Managment.Dtos;

import com.Practice.Parking.Managment.Model.Company;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class GetCompanyResponse {
    Company company;
}
