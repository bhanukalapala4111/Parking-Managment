package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CreateCompanyRequest;
import com.Practice.Parking.Managment.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PostMapping("/create")
    public Long createCompany(@RequestBody CreateCompanyRequest createCompanyRequest){
        return this.companyService.createCompany(createCompanyRequest);
    }
}
