package Projekt.PCCarovnik.dao;

import Projekt.PCCarovnik.models.Konfiguracija;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KonfiguracijaRepository extends JpaRepository<Konfiguracija, Long> {

    @Query("SELECT k FROM Konfiguracija k, Uporabnik u WHERE k.uporabnik = u AND u.id = ?1 ORDER BY k.datumNastanka")
    List<Konfiguracija> getKonfiguracijePoDatumu(@Param("id") Long id);

    List<Konfiguracija> findByUporabnik_id(double uporabnik_id);

}
