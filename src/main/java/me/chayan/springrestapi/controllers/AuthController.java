package me.chayan.springrestapi.controllers;

import me.chayan.springrestapi.models.User;
import me.chayan.springrestapi.payload.request.LoginRequest;
import me.chayan.springrestapi.payload.request.SignupRequest;
import me.chayan.springrestapi.payload.response.BaseResponse;
import me.chayan.springrestapi.payload.response.JwtResponse;
import me.chayan.springrestapi.repositories.UserRepository;
import me.chayan.springrestapi.security.jwt.JwtUtils;
import me.chayan.springrestapi.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.generateJwtToken(authentication);
            long timeout = jwtUtils.getJwtExpirationMs();

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            Optional<User> optionalUser = userRepository.findByEmail(userDetails.getEmail());

            return ResponseEntity.ok(new JwtResponse(true, optionalUser.orElse(null), token, "Bearer", timeout));

        } catch (AuthenticationException e) {
            return ResponseEntity.ok(new BaseResponse(false, "Email or password is incorrect"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        /*if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse(false, "Email is already exists"));
        }*/

        try {
            User user = new User();
            user.setName(signUpRequest.getName());
            user.setPhone(signUpRequest.getPhone());
            user.setEmail(signUpRequest.getEmail());
            user.setPassword(encoder.encode(signUpRequest.getPassword()));

            // Create new user's account
            userRepository.save(user);
            return ResponseEntity.ok(new BaseResponse(true, "Successfully registered"));

        } catch (Exception e) {
            return ResponseEntity
                    .badRequest()
                    .body(new BaseResponse(false, "Email or phone already in used"));
        }
    }

    private boolean authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        } catch (DisabledException e) {
            throw new Exception("User Disabled" + e.getMessage());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
        }
        return false;
    }

}
