package com.Practice.Parking.Managment.Repository;

import com.Practice.Parking.Managment.Model.SlotHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotHistoryRepository extends JpaRepository<SlotHistory, Long> {
    List<SlotHistory> findTop10ByUserIdOrderByBookingDateDesc(Long userId);
}
