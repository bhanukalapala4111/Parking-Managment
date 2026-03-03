package com.Practice.Parking.Managment.Repository;

import com.Practice.Parking.Managment.Model.ParkingSlot;
import com.Practice.Parking.Managment.Model.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    List<ParkingSlot> findByParkingFloorFloorNumberAndStatusOrderBySlotNumberAsc(
            int floorNumber,
            SlotStatus status);

    List<ParkingSlot> findByCompanyIdAndStatus(Long companyId, SlotStatus status);

    List<ParkingSlot> findByOccupantUserId(Long occupantUserId);
}
