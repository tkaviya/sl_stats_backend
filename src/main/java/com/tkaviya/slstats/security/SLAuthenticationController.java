package com.tkaviya.slstats.security;

import com.tkaviya.slstats.model.*;
import com.tkaviya.slstats.respository.SLRoleRepository;
import com.tkaviya.slstats.respository.SLUserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@RestController
//@CrossOrigin
@RequestMapping("/api/auth")
public class SLAuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final SLStatsSecurityUtil slStatsSecurityUtil;

    private final SLRoleRepository slRoleRepository;

    private final SLUserRepository slUserRepository;

    private final PasswordEncoder encoder;

    public SLAuthenticationController(AuthenticationManager authenticationManager,
                                      SLStatsSecurityUtil slStatsSecurityUtil,
                                      SLRoleRepository slRoleRepository,
                                      SLUserRepository slUserRepository,
                                      PasswordEncoder encoder) {
        this.authenticationManager = authenticationManager;
        this.slStatsSecurityUtil = slStatsSecurityUtil;
        this.slRoleRepository = slRoleRepository;
        this.slUserRepository = slUserRepository;
        this.encoder = encoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SLLoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        SLUserDetailsImpl userDetails = (SLUserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = slStatsSecurityUtil.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new SLUserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SLSignUpRequest signUpRequest) {
        if (slUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new SLMessageResponse("Error: Username is already taken!"));
        }

        if (slUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new SLMessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        SLUser user = new SLUser(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<SLRole> roles = new HashSet<>();

        if (strRoles == null) {
            SLRole userRole = slRoleRepository.findByName(SLERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    default:
                        SLRole userRole = slRoleRepository.findByName(SLERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        slUserRepository.save(user);

        return ResponseEntity.ok(new SLMessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = slStatsSecurityUtil.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new SLMessageResponse("You've been signed out!"));
    }
}
