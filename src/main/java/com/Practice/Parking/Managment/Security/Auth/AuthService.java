package com.Practice.Parking.Managment.Security.Auth;

import com.Practice.Parking.Managment.Model.User;
import com.Practice.Parking.Managment.Repository.UserRepository;
import com.Practice.Parking.Managment.Security.Jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;


    public LoginResponse login(LoginRequest request) {


        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();



        String token = jwtUtil.generateToken(user.getId(), user.getRole().name());


        return new LoginResponse(token);
    }
}
