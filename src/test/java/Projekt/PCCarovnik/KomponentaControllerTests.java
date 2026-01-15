package Projekt.PCCarovnik;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import Projekt.PCCarovnik.dao.KomponentaRepository;
import Projekt.PCCarovnik.models.Komponenta;

@SpringBootTest
@AutoConfigureMockMvc
class KomponentaControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KomponentaRepository komponentaRepository;

    @Test
    void testKomponentaExistsAfterSave() {

        Komponenta novaKomponenta = new Komponenta();
        novaKomponenta.setCena(303);

        komponentaRepository.save(novaKomponenta);

        Boolean exists = komponentaRepository.existsById(novaKomponenta.getId());
        assertTrue(exists);
    }

    @Test
    void testSaveKomponentaWithNullValues() {
        Komponenta novaKomponenta = new Komponenta();
        novaKomponenta.setCena(null);
        novaKomponenta.setNaziv(null);
        novaKomponenta.setKonfiguracije(null);
        novaKomponenta.setOpis(null);
        novaKomponenta.setTipKomponente(null);
        novaKomponenta.setUrl(null);
        novaKomponenta.setVrednost1(null);
        novaKomponenta.setVrednost2(null);
        novaKomponenta.setVrednost3(null);
        novaKomponenta.setdatumIzdaje(null);

        komponentaRepository.save(novaKomponenta);

        Boolean exists = komponentaRepository.existsById(novaKomponenta.getId());
        assertTrue(exists);
    }

    @Test
    void testNegativePriceKomponenta() {
        Komponenta novaKomponenta = new Komponenta();
        novaKomponenta.setCena(-333);
        novaKomponenta.setNaziv("TestNegativneCene");

        komponentaRepository.save(novaKomponenta);

        Komponenta fetchedKomponenta = komponentaRepository.findById(novaKomponenta.getId()).orElse(null);
        assertNotNull(fetchedKomponenta);
        assertTrue(fetchedKomponenta.getCena() == -333);
    }

    @Test
    public void testUpdateKomponentaSuccess() throws Exception {

        Komponenta existingKomponenta = new Komponenta();
        existingKomponenta.setNaziv("Old Komponenta");
        komponentaRepository.save(existingKomponenta);

        Komponenta updatedKomponenta = new Komponenta();
        updatedKomponenta.setNaziv("New Komponenta");
        updatedKomponenta.setCena(150);
        updatedKomponenta.setOpis("New description");
        updatedKomponenta.setUrl("new-url");

        String jsonContent = objectMapper.writeValueAsString(updatedKomponenta);

        mockMvc.perform(put("/komponenta/" + existingKomponenta.getId())
                .contentType("application/json")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.naziv", is("New Komponenta")))
                .andExpect(jsonPath("$.cena", is(150)))
                .andExpect(jsonPath("$.opis", is("New description")))
                .andExpect(jsonPath("$.url", is("new-url")));
    }

    @Test
    public void testPrimerjajKomponente() throws Exception {

        mockMvc.perform(get("/komparator/abc/sdf"))
                .andExpect(status().isNotFound());
    }

}
