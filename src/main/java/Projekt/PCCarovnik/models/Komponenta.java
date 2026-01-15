package Projekt.PCCarovnik.models;

import jakarta.persistence.*;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Komponenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "konfiguracija_komponenta", joinColumns = @JoinColumn(name = "komponenta_id"), inverseJoinColumns = @JoinColumn(name = "konfiguracija_id"))
    @JsonIgnore
    private Collection<Konfiguracija> konfiguracije = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "tip_komponente")
    private TipKomponente tipKomponente;

    private String naziv;
    private Integer cena;
    private Date datumIzdaje;
    private String opis;
    private String url;
    private Integer vrednost1;
    private Integer vrednost2;
    private Integer vrednost3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collection<Konfiguracija> getKonfiguracije() {
        return konfiguracije;
    }

    public void setKonfiguracije(Collection<Konfiguracija> konfiguracije) {
        this.konfiguracije = konfiguracije;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Date getdatumIzdaje() {
        return datumIzdaje;
    }

    public void setdatumIzdaje(Date datumIzdaje) {
        this.datumIzdaje = datumIzdaje;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getVrednost1() {
        return vrednost1;
    }

    public void setVrednost1(Integer vrednost1) {
        this.vrednost1 = vrednost1;
    }

    public Integer getVrednost2() {
        return vrednost2;
    }

    public void setVrednost2(Integer vrednost2) {
        this.vrednost2 = vrednost2;
    }

    public Integer getVrednost3() {
        return vrednost3;
    }

    public void setVrednost3(Integer vrednost3) {
        this.vrednost3 = vrednost3;
    }

    public TipKomponente getTipKomponente() {
        return tipKomponente;
    }

    public void setTipKomponente(TipKomponente tipkomponente) {
        this.tipKomponente = tipkomponente;
    }
}
