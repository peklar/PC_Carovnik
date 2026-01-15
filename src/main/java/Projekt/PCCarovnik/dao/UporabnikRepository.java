package Projekt.PCCarovnik.dao;

import Projekt.PCCarovnik.models.Konfiguracija;
import Projekt.PCCarovnik.models.Uporabnik;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UporabnikRepository extends CrudRepository<Uporabnik, Long> {

    @Query("SELECT u FROM Uporabnik u WHERE u.ime = :ime")
    List<Uporabnik> getUporabnikByName(@Param("ime") String ime);

    Boolean existsByIme(String ime);

    Boolean existsByEmail(String email);

    Optional<Uporabnik> findByIme(String ime);

}
