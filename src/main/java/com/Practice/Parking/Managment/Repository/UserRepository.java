package com.Practice.Parking.Managment.Repository;

import com.Practice.Parking.Managment.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User getById(long id);
}
