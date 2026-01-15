package Projekt.PCCarovnik.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import Projekt.PCCarovnik.dao.UporabnikRepository;
import Projekt.PCCarovnik.models.Uporabnik;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UporabnikRepository uporabnikRepository;

    @Autowired
    public CustomUserDetailsService(UporabnikRepository uporabnikRepository) {
        this.uporabnikRepository = uporabnikRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String ime) throws UsernameNotFoundException {
        Uporabnik user = uporabnikRepository.findByIme(ime)
                .orElseThrow(() -> new UsernameNotFoundException("Uporabniško geslo se ne ujema!"));
        return User.builder()
                .username(user.getIme())
                .password(user.getGeslo())
                .build();
    }
}