package com.Practice.Parking.Managment.Controller;

import com.Practice.Parking.Managment.Dtos.CompanyStatsResponse;
import com.Practice.Parking.Managment.Dtos.CreateCompanyRequest;
import com.Practice.Parking.Managment.Dtos.GetCompanyResponse;
import com.Practice.Parking.Managment.Dtos.UpdateCompanyRequest;
import com.Practice.Parking.Managment.Service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @PostMapping("/create")
    public Long createCompany(@RequestBody CreateCompanyRequest createCompanyRequest) {
        return this.companyService.createCompany(createCompanyRequest.toCompany());
    }

    // update company details
    @PatchMapping("/update/{id}")
    public GetCompanyResponse updateCompany(@RequestBody UpdateCompanyRequest updateCompanyRequest,
            @PathVariable("id") Long id) {
        return this.companyService.updateCompany(updateCompanyRequest.toCompany(), id);
    }
    // get company details

    @GetMapping("/get/{Id}")
    public GetCompanyResponse getCompany(@PathVariable("Id") long Id) {
        return this.companyService.getCompany(Id);
    }

    @GetMapping("/all")
    public List<GetCompanyResponse> getAllCompanies() {
        return this.companyService.getAllCompanies();
    }

    @GetMapping("/{companyId}/stats")
    public CompanyStatsResponse getCompanyStats(@PathVariable("companyId") long companyId,
            Authentication authentication) {
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_COMPANY_ADMIN"))) {
            Long authorizedCompanyId = (Long) authentication.getDetails();
            if (authorizedCompanyId == null || !authorizedCompanyId.equals(companyId)) {
                throw new RuntimeException("Unauthorized: You can only view stats for your own company.");
            }
        }
        return this.companyService.getCompanyStats(companyId);
    }
}
