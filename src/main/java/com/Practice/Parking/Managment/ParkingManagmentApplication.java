package com.Practice.Parking.Managment;

import com.Practice.Parking.Managment.Model.Admin;
import com.Practice.Parking.Managment.Model.Role;
import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.AdminRepository;
import com.Practice.Parking.Managment.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class ParkingManagmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkingManagmentApplication.class, args);
	}

	@Bean
	public CommandLineRunner bootstrapAdmin(AdminRepository adminRepository, UserRepository userRepository,
			PasswordEncoder passwordEncoder) {
		return args -> {
			// 1. Migrate existing ADMIN users from User table to Admin table
			List<User> existingAdmins = userRepository.findAll().stream()
					.filter(u -> u.getRole() == Role.ADMIN)
					.toList();

			if (!existingAdmins.isEmpty()) {
				System.out.println("Migrating " + existingAdmins.size() + " existing ADMIN users to Admin table...");
				for (User u : existingAdmins) {
					if (adminRepository.findByEmail(u.getEmail()).isEmpty()) {
						Admin admin = Admin.builder()
								.userName(u.getUserName())
								.email(u.getEmail())
								.password(u.getPassword()) // Already encoded
								.build();
						adminRepository.save(admin);
					}
					userRepository.delete(u);
				}
				System.out.println("Migration complete.");
			}

			// 2. Create default Admin if still empty
			if (adminRepository.count() == 0) {
				Admin admin = Admin.builder()
						.userName("Super Admin")
						.email("admin@admin.com")
						.password(passwordEncoder.encode("admin123"))
						.build();
				adminRepository.save(admin);
				System.out.println("Default Super Admin created: admin@admin.com / admin123");
			}
		};
	}

}