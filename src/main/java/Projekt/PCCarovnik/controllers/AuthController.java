package Projekt.PCCarovnik.controllers;

import java.util.HashMap;
import java.util.Properties;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import java.util.HashMap;
import java.util.Map;

import Projekt.PCCarovnik.dao.RoleRepository;
import Projekt.PCCarovnik.dao.UporabnikRepository;
import Projekt.PCCarovnik.models.Uporabnik;
import Projekt.PCCarovnik.services.EmailService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


    

    private AuthenticationManager authenticationManager;
    private UporabnikRepository uporabnikRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UporabnikRepository uporabnikRepository,
            PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.uporabnikRepository = uporabnikRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @CrossOrigin
    @PostMapping("login")
    public ResponseEntity<String> login(@RequestBody Uporabnik LoginDto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(LoginDto.getIme(), LoginDto.getGeslo()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userIme = LoginDto.getIme();
        return new ResponseEntity<>("Uporabnik je uspešno prijavljen!" + userIme, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody Uporabnik registerDto) {
        if (uporabnikRepository.existsByIme(registerDto.getIme())) {
            return new ResponseEntity<>("Neustrezno uporabniško ime", HttpStatus.BAD_REQUEST);
        } else if (uporabnikRepository.existsByEmail(registerDto.getEmail())) {
            return new ResponseEntity<>("Email že obstaja", HttpStatus.BAD_REQUEST);

        }

        Uporabnik user = new Uporabnik();
        user.setIme(registerDto.getIme());
        user.setEmail(registerDto.getEmail());
        user.setGeslo(passwordEncoder.encode(registerDto.getGeslo()));

        String to = user.getEmail();
        String subject = "Registration Confirmation";
        String body = "Thank you for registering!";
        Properties props = new Properties();
        props.put("mail.debug", "true");
        emailService.sendRegistrationEmail(to, subject, body);

        uporabnikRepository.save(user);

        return new ResponseEntity<>("Uporabnik je uspe�no prijavljen!", HttpStatus.OK);
    }
}
