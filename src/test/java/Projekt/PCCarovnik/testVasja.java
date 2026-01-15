package Projekt.PCCarovnik;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import Projekt.PCCarovnik.dao.UporabnikRepository;
import Projekt.PCCarovnik.models.Uporabnik;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class testVasja {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UporabnikRepository uporabnikRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Test za dodajanje uporabnika v bazo!
    @Test
    void testIme() {
        Uporabnik newUser = new Uporabnik();
        newUser.setIme("Janezek");

        uporabnikRepository.save(newUser);

        Boolean exists = uporabnikRepository.existsByIme("Janezek");

        assertTrue(exists);
    }

    // Test za preverjanje ali se uporabniksko geslo ustrezno hashira pri
    // registraciji.
    @Test
    void testHash() throws Exception {
        Uporabnik registerDto = new Uporabnik();
        registerDto.setIme("testuser1234567");
        registerDto.setEmail("testuser@example1234567.com");
        registerDto.setGeslo("bednogeslo");

        MvcResult result = mockMvc.perform(post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDto)))
                .andExpect(status().isOk())
                .andReturn();

        Uporabnik createdUser = uporabnikRepository.findByIme("testuser12345").orElse(null);

        assertThat(createdUser.getGeslo()).isNotEqualTo("bednogeslo");

        boolean passwordMatches = passwordEncoder.matches("bednogeslo", createdUser.getGeslo());
        assertThat(passwordMatches).isTrue();
    }

    // Test da se z updajtanjem uporabnik ustrezno posodobi
    @Test
    void testUpdateUporabnik() throws Exception {
        Uporabnik existingUser = new Uporabnik();
        existingUser.setIme("OldUser");
        existingUser.setEmail("olduser@example.com");
        existingUser.setGeslo("oldpassword");

        Uporabnik shranjenUporabnik = uporabnikRepository.save(existingUser);

        Uporabnik updatedUporabnik = new Uporabnik();
        updatedUporabnik.setIme("UpdatedUser");
        updatedUporabnik.setEmail("updateduser@example.com");
        updatedUporabnik.setGeslo("newpassword");

        mockMvc.perform(put("/uporabnik/" + shranjenUporabnik.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUporabnik)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ime").value("UpdatedUser"));
    }

    // test za preverjanje ali se uporabnik ustrezno izbrise
    @Test
    void testDeleteUser() throws Exception {
        Uporabnik user = new Uporabnik();
        user.setIme("UserToDelete");
        user.setEmail("user@delete.com");
        user.setGeslo("deletepassword");

        Uporabnik savedUser = uporabnikRepository.save(user);

        mockMvc.perform(delete("/uporabnik/" + savedUser.getId()))
                .andExpect(status().isOk());

        assertThat(uporabnikRepository.existsById(savedUser.getId())).isFalse();
    }

    // test za getanje uporabnika glede na njegovo ime
    @Test
    void testGetUserByName() throws Exception {
        Uporabnik user = new Uporabnik();
        user.setIme("User1");
        user.setEmail("user1@example.com");
        user.setGeslo("password1");

        uporabnikRepository.save(user);

        mockMvc.perform(get("/uporabnik/uporabnikIme/User1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].ime").value("User1"));
    }

}
