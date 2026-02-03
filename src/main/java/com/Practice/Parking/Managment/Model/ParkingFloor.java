package com.Practice.Parking.Managment.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class ParkingFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private int floorNumber;

    @Column
    private int floorCapacity;

    @Column
    private int availableCapacity;

    @Builder.Default
    @OneToMany(mappedBy = "parkingFloor",cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnoreProperties("parkingFloor")
    private List<ParkingSlot> slots = new ArrayList<>();
}
