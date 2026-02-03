package com.Practice.Parking.Managment.Service;

import com.Practice.Parking.Managment.Dtos.CreateCompanyRequest;
import com.Practice.Parking.Managment.Dtos.GetCompanyResponse;
import com.Practice.Parking.Managment.Model.Company;
import com.Practice.Parking.Managment.Repository.CompanyRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    CompanyRepository companyRepository;

    public Long createCompany(Company company){
         return this.companyRepository.save(company).getId();
    }
    public GetCompanyResponse getCompany(long Id){
        return GetCompanyResponse.builder().company(companyRepository.findById(Id)).build();
    }
    public int findAvailableCapacityByCompanyName(String companyName){
        Company company= this.companyRepository.findByCompanyName(companyName).orElseThrow(() -> new RuntimeException("Company not found"));
        return company.getAvailableCapacity();

    }
    public Company findByCompanyName(String name){
        return companyRepository.findByCompanyName(name).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    public GetCompanyResponse updateCompany(Company company ,long id){
        Company existCompany= companyRepository.findById(id);

        Company updatedCompany= this.merge(existCompany,company);

        Company s = this.companyRepository.save(updatedCompany);

        return GetCompanyResponse.builder().company(updatedCompany).build();

    }

    private Company merge(Company existCompany, Company updatedCompany){

        if(updatedCompany.getCompanyName() != null && existCompany.getCompanyName()!=updatedCompany.getCompanyName()){
            existCompany.setCompanyName(updatedCompany.getCompanyName());
        }
        if(updatedCompany.getTotalCapacity()>0 && existCompany.getTotalCapacity()!=updatedCompany.getTotalCapacity()){
            int diff=updatedCompany.getTotalCapacity()-existCompany.getTotalCapacity();
            existCompany.setTotalCapacity(updatedCompany.getTotalCapacity());
            existCompany.setTotalCapacity(existCompany.getAvailableCapacity()+diff);
        }
        return existCompany;
    }

}
