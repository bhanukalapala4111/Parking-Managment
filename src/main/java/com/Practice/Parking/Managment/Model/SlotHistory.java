package com.Practice.Parking.Managment.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SlotHistory")
public class SlotHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long slotId;

    private int slotNumber;

    private int floorNumber;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date bookingDate;
}
