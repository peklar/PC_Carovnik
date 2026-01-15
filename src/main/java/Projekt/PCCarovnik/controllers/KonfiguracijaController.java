package Projekt.PCCarovnik.controllers;

import Projekt.PCCarovnik.dao.KonfiguracijaRepository;
import Projekt.PCCarovnik.dao.KomponentaRepository;
import Projekt.PCCarovnik.dao.UporabnikRepository;
import Projekt.PCCarovnik.models.Konfiguracija;
import Projekt.PCCarovnik.models.Komponenta;
import Projekt.PCCarovnik.models.Uporabnik;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/konfiguracija")

public class KonfiguracijaController {

    @Autowired
    private KonfiguracijaRepository konfiguracijaDao;

    @Autowired
    private KomponentaRepository komponentaDao;

    @Autowired
    private UporabnikRepository uporabnikDao;

    @GetMapping
    public Iterable<Konfiguracija> getVseKonfiguracije() {
        return konfiguracijaDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Konfiguracija> getKonfiguracija(@PathVariable Long id) {
        return konfiguracijaDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id_uporabnik}")
    public List<Konfiguracija> getKonfiguracijaByUserId(@PathVariable(name = "id_uporabnik") Long id) {
        return konfiguracijaDao.findByUporabnik_id(id);
    }

    @GetMapping("/datumOrder/{id_uporabnik}")
    public Iterable<Konfiguracija> getKonfiguracijePoDatum(@PathVariable(name = "id_uporabnik") Long id) {
        return konfiguracijaDao.getKonfiguracijePoDatumu(id);
    }

    @PostMapping("/{id_uporabnik}/{naziv}")
    public Optional<Konfiguracija> dodajKonfiguracijo(@PathVariable(name = "id_uporabnik") Long id,@PathVariable(name = "naziv") String naziv) {
        return uporabnikDao.findById(id).map(uporabnik -> {
            Konfiguracija konfiguracija = new Konfiguracija();
            konfiguracija.setNaziv(naziv);
            konfiguracija.setUporabnik(uporabnik);
            konfiguracija.setdatumNastanka();
            return konfiguracijaDao.save(konfiguracija);
        });
    }

    // dodaj komponento v konfiguracijo
    @PostMapping("/dodajKomponento/{id1}/{id2}")
    public ResponseEntity<Konfiguracija> dodajKomponentoKonfiguraciji(@PathVariable Long id1, @PathVariable Long id2) {

        Optional<Konfiguracija> konfiguracijaOpt = konfiguracijaDao.findById(id1);
        Optional<Komponenta> komponentaOpt = komponentaDao.findById(id2);

        if (konfiguracijaOpt.isPresent() && komponentaOpt.isPresent()) {
            Konfiguracija konfiguracija = konfiguracijaOpt.get();
            Komponenta komponenta = komponentaOpt.get();

            konfiguracija.addKomponenta(komponenta);

            konfiguracijaDao.save(konfiguracija);
            return ResponseEntity.ok(konfiguracija);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Konfiguracija> updateKonfiguracija(@PathVariable Long id,
            @RequestBody Konfiguracija updatedKonfiguracija) {
        return konfiguracijaDao.findById(id)
                .map(existingKonfiguracija -> {
                    existingKonfiguracija.setNaziv(updatedKonfiguracija.getNaziv());
                    Konfiguracija savedKonfiguracija = konfiguracijaDao.save(existingKonfiguracija);
                    return ResponseEntity.ok(savedKonfiguracija);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> izbrisiKonfiguracijo(@PathVariable Long id) {
        konfiguracijaDao.deleteById(id);

        var exists = konfiguracijaDao.existsById(id);

        if (!exists)
            return new ResponseEntity<>(id, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/odstraniKomponento/{id1}/{id2}")
    public ResponseEntity<Konfiguracija> odstraniKomponentoKonfiguraciji(@PathVariable Long id1,
            @PathVariable Long id2) {

        Optional<Konfiguracija> konfiguracijaOpt = konfiguracijaDao.findById(id1);
        Optional<Komponenta> komponentaOpt = komponentaDao.findById(id2);

        if (konfiguracijaOpt.isPresent() && komponentaOpt.isPresent()) {
            Konfiguracija konfiguracija = konfiguracijaOpt.get();
            Komponenta komponenta = komponentaOpt.get();

            konfiguracija.removeKomponenta(komponenta);

            konfiguracijaDao.save(konfiguracija);
            return ResponseEntity.ok(konfiguracija);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
