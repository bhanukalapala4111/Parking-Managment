package com.Practice.Parking.Managment.Security.Auth;

import com.Practice.Parking.Managment.Model.Admin;
import com.Practice.Parking.Managment.Model.Role;
import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.AdminRepository;
import com.Practice.Parking.Managment.Repository.UserRepository;
import com.Practice.Parking.Managment.Security.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            Optional<Admin> admin = adminRepository.findByEmail(request.getEmail());
            if (admin.isPresent()) {
                Admin a = admin.get();
                String token = jwtUtil.generateToken(a.getId(), "ADMIN", 0L);
                return new LoginResponse(a.getId(), token, a.getUserName(), Role.ADMIN);
            }

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found after successful authentication"));

            if (user.getRole() == null) {
                throw new RuntimeException("User role is not assigned");
            }

            String token = jwtUtil.generateToken(user.getId(), user.getRole().name(), user.getCompanyId());

            return new LoginResponse(user.getId(), token, user.getUserName(), user.getRole());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
