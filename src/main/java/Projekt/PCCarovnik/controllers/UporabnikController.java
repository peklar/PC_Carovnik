package Projekt.PCCarovnik.controllers;

import Projekt.PCCarovnik.dao.UporabnikRepository;
import Projekt.PCCarovnik.models.Konfiguracija;
import Projekt.PCCarovnik.models.Uporabnik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin
@RestController
@RequestMapping("/uporabnik")

public class UporabnikController {

    @Autowired
    private UporabnikRepository uporabnikDao;

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @GetMapping
    public Iterable<Uporabnik> vrniUporabnike() {
        return uporabnikDao.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Uporabnik> vrniUporabnikaPoId(@PathVariable Long id) {
        return uporabnikDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/uporabnikIme/{uporabnik_ime}")
    public Iterable<Uporabnik> getUporabnikByName(@PathVariable(name = "uporabnik_ime") String ime) {
        return uporabnikDao.getUporabnikByName(ime);
    }

    @PostMapping
    public Uporabnik dodajUporabnik(@RequestBody Uporabnik uporabnik) {
        return uporabnikDao.save(uporabnik);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Uporabnik> updateUporabnik(@PathVariable Long id, @RequestBody Uporabnik updatedUporabnik) {
        return uporabnikDao.findById(id)
                .map(existingUporabnik -> {
                    existingUporabnik.setIme(updatedUporabnik.getIme());
                    existingUporabnik.setGeslo(updatedUporabnik.getGeslo());
                    existingUporabnik.setEmail(updatedUporabnik.getEmail());
                    Uporabnik savedUporabnik = uporabnikDao.save(existingUporabnik);
                    return ResponseEntity.ok(savedUporabnik);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> izbrisiUporabnika(@PathVariable Long id) {
        uporabnikDao.deleteById(id);

        var exists = uporabnikDao.existsById(id);

        if (!exists)
            return new ResponseEntity<>(id, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
