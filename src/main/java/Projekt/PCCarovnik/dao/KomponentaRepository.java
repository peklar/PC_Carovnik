package Projekt.PCCarovnik.dao;

import Projekt.PCCarovnik.models.Komponenta;
import Projekt.PCCarovnik.models.TipKomponente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

public interface KomponentaRepository extends JpaRepository<Komponenta, Long> {

    // @Query("SELECT k FROM Komponenta k WHERE k.konfiguracija.id =
    // :konfiguracijaId")
    // List<Komponenta> findByKonfiguracijaId(@Param("konfiguracijaId") Long
    // konfiguracijaId);

    List<Komponenta> findByTipKomponente(TipKomponente tipKomponente);

    // query za komparator
    @Query("SELECT k FROM Komponenta k WHERE k.id IN (:id1, :id2)")
    List<Komponenta> findByIds(@Param("id1") Long id1, @Param("id2") Long id2);

    // query za search po nazivu
    @Query("SELECT k FROM Komponenta k WHERE LOWER(k.naziv) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Komponenta> findByKeyword(@Param("keyword") String keyword);

    List<Komponenta> findByTipKomponenteAndCenaBetweenAndVrednost1BetweenAndVrednost2BetweenAndVrednost3Between(
            TipKomponente tipKomponente, Integer minPrice,
            Integer maxPrice, Integer minVrednost1,
            Integer maxVrednost1, Integer minVrednost2, Integer maxVrednost2, Integer minVrednost3,
            Integer maxVrednost3);

    List<Komponenta> findByKonfiguracije_id(Long id);

}