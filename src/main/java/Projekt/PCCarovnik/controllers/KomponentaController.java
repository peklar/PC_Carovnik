package Projekt.PCCarovnik.controllers;

import Projekt.PCCarovnik.dao.KomponentaRepository;
import Projekt.PCCarovnik.dao.KonfiguracijaRepository;
import Projekt.PCCarovnik.models.Komponenta;
import Projekt.PCCarovnik.models.Konfiguracija;
import Projekt.PCCarovnik.models.TipKomponente;
import Projekt.PCCarovnik.services.PolnenjeBazeService;
import Projekt.PCCarovnik.services.PrimerjavaKomponentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/komponenta")
public class KomponentaController {

    @Autowired
    private KomponentaRepository komponentaDao;

    @Autowired
    private PolnenjeBazeService PolnenjeBazeService;

    @Autowired
    private KonfiguracijaRepository konfiguracijaDao;

    @Autowired
    private PrimerjavaKomponentService PrimerjavaKomponentService;

    @PostMapping("/napolniKomponente")
    public ResponseEntity<String> executeStoredProcedure() {
        try {
            PolnenjeBazeService.napolniKomponente();
            return ResponseEntity.ok("Stored procedure executed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error executing stored procedure");
        }
    }

    @GetMapping
    public Iterable<Komponenta> getAllKomponentas() {
        return komponentaDao.findAll();
    }

    // findbyid
    @GetMapping("/{id}")
    public ResponseEntity<Komponenta> getKomponenta(@PathVariable Long id) {
        return komponentaDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // vsi komponenti v eni konfiguraciji
    // @GetMapping("/byKonfiguracija/{id_konfiguracija}")
    // public List<Komponenta> getKomponentasByKonfiguracijaId(@PathVariable Long
    // id_konfiguracija) {
    // return komponentaDao.findByKonfiguracijaId(id_konfiguracija);
    // }

    // search po nazivu
    @GetMapping("/search/{keyword}")
    public List<Komponenta> getKomponentasByKeyword(@PathVariable String keyword) {
        return komponentaDao.findByKeyword(keyword);
    }

    // sort by vrednosti
    @GetMapping("/{way}/{cat}")
    public Iterable<Komponenta> getOrderedKomponente(@PathVariable String cat, @PathVariable String way) {

        Sort sort;
        if (way.equals("desc")) {
            sort = Sort.by(Sort.Direction.DESC, cat);
        } else {
            sort = Sort.by(Sort.Direction.ASC, cat);
        }

        return komponentaDao.findAll(sort);
    }

    // search by vrednost enum (tip komponente)
    @GetMapping("/Type/{tip}")
    public List<Komponenta> getKomponenteByTipKomponente(@PathVariable String tip) {
        TipKomponente tipEnum = TipKomponente.valueOf(tip);

        return komponentaDao.findByTipKomponente(tipEnum);
    }

    // za primerjanje dveh komponent aka. kira je jača od druge.
    @GetMapping("/komparator/{id1}/{id2}")
    public ResponseEntity<ArrayList<String>> PrimerjajKomponente(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Komponenta> komponenta1 = komponentaDao.findById(id1);
        Optional<Komponenta> komponenta2 = komponentaDao.findById(id2);

        if (komponenta1.isPresent() && komponenta2.isPresent()) {
            PrimerjavaKomponentService serviceInstance = PrimerjavaKomponentService;

            ArrayList<String> comparisonResult = serviceInstance.PrimerjajKomponente(
                    komponenta1.get(),
                    komponenta2.get());
            return ResponseEntity.ok(comparisonResult);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/pridobiKomponente/{id_konfiguracija}")
    public List<Komponenta> getKomponentaByKonfiguracijaId(@PathVariable(name = "id_konfiguracija") Long id) {
        return komponentaDao.findByKonfiguracije_id(id);
    }

    // searchanje komponent glede na vse kar ti srce pozeli
    @GetMapping("/searchByEverything")
    public List<Komponenta> getKomponentasByPriceAndVrednost1(
            @RequestParam(name = "type") String tipKomponente,
            @RequestParam(name = "minPrice") Integer minPrice,
            @RequestParam(name = "maxPrice") Integer maxPrice,
            @RequestParam(name = "minVrednost1") Integer minVrednost1,
            @RequestParam(name = "maxVrednost1") Integer maxVrednost1,
            @RequestParam(name = "minVrednost2") Integer minVrednost2,
            @RequestParam(name = "maxVrednost2") Integer maxVrednost2,
            @RequestParam(name = "minVrednost3") Integer minVrednost3,
            @RequestParam(name = "maxVrednost3") Integer maxVrednost3) {
                
        return komponentaDao.findByTipKomponenteAndCenaBetweenAndVrednost1BetweenAndVrednost2BetweenAndVrednost3Between(TipKomponente.valueOf(tipKomponente), minPrice,
                maxPrice, minVrednost1, maxVrednost1, minVrednost2, maxVrednost2, minVrednost3, maxVrednost3);
    }

    @PostMapping
    public Komponenta dodajKomponento(@RequestBody Komponenta komponenta) {
        return komponentaDao.save(komponenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Komponenta> updateKomponenta(@PathVariable Long id,
            @RequestBody Komponenta updatedKomponenta) {
        return komponentaDao.findById(id)
                .map(existingKomponenta -> {
                    existingKomponenta.setNaziv(updatedKomponenta.getNaziv());
                    existingKomponenta.setCena(updatedKomponenta.getCena());
                    existingKomponenta.setOpis(updatedKomponenta.getOpis());
                    existingKomponenta.setUrl(updatedKomponenta.getUrl());
                    Komponenta savedKomponenta = komponentaDao.save(existingKomponenta);
                    return ResponseEntity.ok(savedKomponenta);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> izbrisiKomponento(@PathVariable Long id) {
        komponentaDao.deleteById(id);

        boolean exists = komponentaDao.existsById(id);

        if (!exists) {
            return new ResponseEntity<>(id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
