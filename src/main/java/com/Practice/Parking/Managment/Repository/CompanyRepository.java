package com.Practice.Parking.Managment.Repository;

import com.Practice.Parking.Managment.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByCompanyName(String companyName);

    Company findById(long companyId);
}
