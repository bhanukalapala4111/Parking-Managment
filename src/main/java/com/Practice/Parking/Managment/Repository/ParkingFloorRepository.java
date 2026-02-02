package com.Practice.Parking.Managment.Repository;

import com.Practice.Parking.Managment.Model.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor, Long> {
    public List<ParkingFloor> findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(int value);
}
