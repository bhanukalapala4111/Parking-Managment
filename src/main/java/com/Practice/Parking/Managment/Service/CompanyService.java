package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CompanyStatsResponse;
import com.Practice.Parking.Managment.Dtos.GetCompanyResponse;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Model.SlotStatus;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import com.Practice.Parking.Managment.Repository.ParkingSlotRepository;
import com.Practice.Parking.Managment.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ParkingSlotRepository parkingSlotRepository;

    public Long createCompany(Company company) {
        return this.companyRepository.save(company).getId();
    }

    public GetCompanyResponse getCompany(long Id) {
        return GetCompanyResponse.builder().company(companyRepository.findById(Id)).build();
    }

    public int findAvailableCapacityByCompanyName(String companyName) {
        Company company = this.companyRepository.findByCompanyName(companyName)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return company.getAvailableCapacity();

    }

    public Company findByCompanyName(String name) {
        return companyRepository.findByCompanyName(name).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public GetCompanyResponse updateCompany(Company company, long id) {
        Company existCompany = companyRepository.findById(id);

        Company updatedCompany = this.merge(existCompany, company);

        Company s = this.companyRepository.save(updatedCompany);

        return GetCompanyResponse.builder().company(updatedCompany).build();

    }

    private Company merge(Company existCompany, Company updatedCompany) {

        if (updatedCompany.getCompanyName() != null
                && existCompany.getCompanyName() != updatedCompany.getCompanyName()) {
            existCompany.setCompanyName(updatedCompany.getCompanyName());
        }
        if (updatedCompany.getTotalCapacity() > 0
                && existCompany.getTotalCapacity() != updatedCompany.getTotalCapacity()) {
            int diff = updatedCompany.getTotalCapacity() - existCompany.getTotalCapacity();
            existCompany.setTotalCapacity(updatedCompany.getTotalCapacity());
            existCompany.setAvailableCapacity(existCompany.getAvailableCapacity() + diff);
        }
        return existCompany;
    }

    public List<GetCompanyResponse> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(company -> GetCompanyResponse.builder().company(company).build())
                .collect(Collectors.toList());
    }

    public CompanyStatsResponse getCompanyStats(long companyId) {
        long userCount = userRepository.findByCompanyId(companyId).size();
        int totalSlots = parkingSlotRepository.findByCompanyId(companyId).size();
        int availableSlots = parkingSlotRepository.findByCompanyIdAndStatus(companyId, SlotStatus.AVAILABLE).size();
        int occupiedSlots = parkingSlotRepository.findByCompanyIdAndStatus(companyId, SlotStatus.OCCUPIED).size();

        return CompanyStatsResponse.builder()
                .userCount(userCount)
                .totalSlots(totalSlots)
                .availableSlots(availableSlots)
                .occupiedSlots(occupiedSlots)
                .build();
    }
}
