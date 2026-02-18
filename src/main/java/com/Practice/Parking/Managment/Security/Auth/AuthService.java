package com.Practice.Parking.Managment.Security.Auth;

import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.UserRepository;
import com.Practice.Parking.Managment.Security.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public LoginResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()));

            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("User not found after successful authentication"));

            if (user.getRole() == null) {
                throw new RuntimeException("User role is not assigned");
            }

            String token = jwtUtil.generateToken(user.getId(), user.getRole().name());

            return new LoginResponse(token, user.getUserName(), user.getRole());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid email or password");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
