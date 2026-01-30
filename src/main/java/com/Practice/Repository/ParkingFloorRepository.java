package com.Practice.Repository;

import com.Practice.Model.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ParkingFloorRepository extends JpaRepository<ParkingFloor, Integer> {
}
