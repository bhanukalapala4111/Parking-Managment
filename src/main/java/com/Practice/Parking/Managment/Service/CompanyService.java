package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CreateCompanyRequest;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Long createCompany(CreateCompanyRequest createCompanyRequest){
        Company company = Company.builder().companyName(createCompanyRequest.getCompanyName()).totalCapacity(createCompanyRequest.getTotalCapacity()).availableCapacity(createCompanyRequest.getTotalCapacity()).build();
        return this.companyRepository.save(company).getId();
    }
}
