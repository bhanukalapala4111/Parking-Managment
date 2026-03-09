package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.GetCompanyResponse;
import com.Practice.Parking.Managment.Dtos.GetUserResponse;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.ParkingFloor;
import com.Practice.Parking.Managment.Model.ParkingSlot;
import com.Practice.Parking.Managment.Model.SlotHistory;
import com.Practice.Parking.Managment.Model.SlotStatus;
import com.Practice.Parking.Managment.Repository.ParkingSlotRepository;
import com.Practice.Parking.Managment.Repository.SlotHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingSlotService {

    @Autowired
    ParkingFloorService parkingFloorService;

    @Autowired
    ParkingSlotRepository parkingSlot;

    @Autowired
    UserService userService;

    @Autowired
    CompanyService companyService;

    @Autowired
    SlotHistoryRepository slotHistoryRepository;

    @Transactional
    public long getSlot(long userId) {

        GetUserResponse userResponse = userService.getUser(userId);

        if (companyService.findAvailableCapacityByCompanyName(
                companyService.getCompany(userResponse.getUser().getCompanyId()).getCompany().getCompanyName()) <= 0) {
            throw new RuntimeException("Company parking capacity exhausted");
        }

        List<ParkingFloor> floors = parkingFloorService.findByAvailableCapacityGreaterThanOrderByFloorNumberAsc(0);

        if (floors.isEmpty()) {
            throw new RuntimeException("No parking floors available");
        }

        ParkingFloor floor = floors.get(0);

        List<ParkingSlot> slots = parkingSlot.findByParkingFloorFloorNumberAndStatusOrderBySlotNumberAsc(
                floor.getFloorNumber(), SlotStatus.AVAILABLE);

        if (slots.isEmpty()) {
            throw new RuntimeException("No slots available on floor");
        }

        ParkingSlot slot = slots.get(0);
        slot.setStatus(SlotStatus.OCCUPIED);
        slot.setCompanyId(userResponse.getUser().getCompanyId());
        slot.setOccupantUserId(userId);

        floor.setAvailableCapacity(floor.getAvailableCapacity() - 1);

        Company company = companyService.findByCompanyName(
                companyService.getCompany(userResponse.getUser().getCompanyId()).getCompany().getCompanyName());
        company.setAvailableCapacity(company.getAvailableCapacity() - 1);

        parkingSlot.save(slot);
        // floor & company auto-persist due to @Transactional

        return slot.getId();
    }

    @Transactional
    public boolean releaseSlot(long id) {

        ParkingSlot releaseParkingSlot = this.parkingSlot.findById(id)
                .orElseThrow(() -> new RuntimeException("No Slot with the given ID"));

        // Save history before clearing occupant data
        if (releaseParkingSlot.getOccupantUserId() != null && releaseParkingSlot.getOccupantUserId() != 0) {
            SlotHistory history = SlotHistory.builder()
                    .userId(releaseParkingSlot.getOccupantUserId())
                    .slotId(releaseParkingSlot.getId())
                    .slotNumber(releaseParkingSlot.getSlotNumber())
                    .floorNumber(releaseParkingSlot.getParkingFloor().getFloorNumber())
                    .build();
            slotHistoryRepository.save(history);
        }

        releaseParkingSlot.setStatus(SlotStatus.AVAILABLE);

        Company company = companyService.getCompany(releaseParkingSlot.getCompanyId()).getCompany();
        company.setAvailableCapacity(company.getAvailableCapacity() + 1);
        releaseParkingSlot.getParkingFloor()
                .setAvailableCapacity(releaseParkingSlot.getParkingFloor().getAvailableCapacity() + 1);
        releaseParkingSlot.setCompanyId(0L);
        releaseParkingSlot.setOccupantUserId(0L);
        this.parkingSlot.save(releaseParkingSlot);
        return true;
    }

    public long addSlot(ParkingSlot parkingSlot1) {
        return parkingSlot.save(parkingSlot1).getId();
    }

    public List<ParkingSlot> getUserBookings(long userId) {
        return parkingSlot.findByOccupantUserId(userId);
    }

    public List<ParkingSlot> getSlotsByCompany(long companyId) {
        return parkingSlot.findByCompanyId(companyId);
    }
}
