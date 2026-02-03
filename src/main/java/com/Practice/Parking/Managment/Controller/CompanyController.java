package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CreateCompanyRequest;
import com.Practice.Parking.Managment.Dtos.GetCompanyResponse;
import com.Practice.Parking.Managment.Dtos.UpdateCompanyRequest;
import com.Practice.Parking.Managment.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PostMapping("/create")
    public Long createCompany(@RequestBody CreateCompanyRequest createCompanyRequest){
        return this.companyService.createCompany(createCompanyRequest.toComapny());
    }

    //update company details
    @PatchMapping("/update")
    public GetCompanyResponse updateCompany(@RequestBody UpdateCompanyRequest updateCompanyRequest, Long id){
        return this.companyService.updateCompany(updateCompanyRequest.toCompany(),id);
    }
    //get company details

    @GetMapping("/get/{Id}")
    public GetCompanyResponse getCompany(@PathVariable long Id){
        return this.companyService.getCompany(Id);
    }
}
