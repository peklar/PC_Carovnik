package Projekt.PCCarovnik;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import Projekt.PCCarovnik.dao.KonfiguracijaRepository;
import Projekt.PCCarovnik.dao.KomponentaRepository;
import Projekt.PCCarovnik.dao.UporabnikRepository;
import Projekt.PCCarovnik.models.Konfiguracija;
import Projekt.PCCarovnik.models.Komponenta;
import Projekt.PCCarovnik.models.Uporabnik;

@SpringBootTest
@AutoConfigureMockMvc
class KonfiguracijaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KonfiguracijaRepository konfiguracijaRepository;

    @Autowired
    private UporabnikRepository uporabnikRepository;

    @Autowired
    private KomponentaRepository komponentaRepository;

    // Ustvari konfiguracijo in jo shrani v repository
    @Test
    void testKonfiguracijaExistsAfterSave() {
        Konfiguracija novaKonfiguracija = new Konfiguracija();
        novaKonfiguracija.setNaziv("Testna Konfiguracija");
        novaKonfiguracija.setdatumNastanka();

        konfiguracijaRepository.save(novaKonfiguracija);

        Boolean exists = konfiguracijaRepository.existsById(novaKonfiguracija.getId());
        assertTrue(exists);
    }

    // Posodobimo obstojeco konfiguracijo
    @Test
    public void testUpdateKonfiguracijaSuccess() throws Exception {
        Konfiguracija existingKonfiguracija = new Konfiguracija();
        existingKonfiguracija.setNaziv("Stara konfiguracija");
        konfiguracijaRepository.save(existingKonfiguracija);

        Konfiguracija updatedKonfiguracija = new Konfiguracija();
        updatedKonfiguracija.setNaziv("Nova konfiguracija");

        String jsonContent = objectMapper.writeValueAsString(updatedKonfiguracija);

        mockMvc.perform(put("/konfiguracija/" + existingKonfiguracija.getId())
                .contentType("application/json")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv", is("Nova konfiguracija")));
    }

    // Dodamo komponento v konfiguracijo
    @Test
    public void testAddKomponentaToKonfiguracija() throws Exception {
        Komponenta novaKomponenta = new Komponenta();
        novaKomponenta.setNaziv("Testna Komponenta");
        komponentaRepository.save(novaKomponenta);

        Konfiguracija novaKonfiguracija = new Konfiguracija();
        novaKonfiguracija.setNaziv("Konfiguracija SuperMasina");
        konfiguracijaRepository.save(novaKonfiguracija);

        mockMvc.perform(post("/konfiguracija/dodajKomponento/" + novaKonfiguracija.getId() + "/" + novaKomponenta.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.komponente[0].naziv", is("Testna Komponenta")));
    }

    // Odstranimo komponento iz konfiguracije
    @Test
    public void testRemoveKomponentaFromKonfiguracija() throws Exception {
        Komponenta komponenta = new Komponenta();
        komponenta.setNaziv("Komponenta za izbris");
        komponentaRepository.save(komponenta);

        Konfiguracija konfiguracija = new Konfiguracija();
        konfiguracija.setNaziv("Konfiguracija s komponento");
        konfiguracija.addKomponenta(komponenta);
        konfiguracijaRepository.save(konfiguracija);

        mockMvc.perform(delete("/konfiguracija/odstraniKomponento/" + konfiguracija.getId() + "/" + komponenta.getId()))
                .andExpect(status().isOk());

        Konfiguracija updatedKonfiguracija = konfiguracijaRepository.findById(konfiguracija.getId()).orElse(null);
        assertNotNull(updatedKonfiguracija);
        assertTrue(updatedKonfiguracija.getKomponente().isEmpty());
    }

    // Odstranimo konfiguracijo in preverimo, če je bila odstranjena
    @Test
    public void testDeleteKonfiguracija() throws Exception {
        Konfiguracija novaKonfiguracija = new Konfiguracija();
        novaKonfiguracija.setNaziv("Konfiguracija za izbris");
        konfiguracijaRepository.save(novaKonfiguracija);

        mockMvc.perform(delete("/konfiguracija/" + novaKonfiguracija.getId()))
                .andExpect(status().isOk());

        Boolean exists = konfiguracijaRepository.existsById(novaKonfiguracija.getId());
        assertTrue(!exists);
    }
}
