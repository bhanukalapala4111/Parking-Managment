package com.Practice.Parking.Managment.Dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyStatsResponse {
    private long userCount;
    private int totalSlots;
    private int availableSlots;
    private int occupiedSlots;
}
