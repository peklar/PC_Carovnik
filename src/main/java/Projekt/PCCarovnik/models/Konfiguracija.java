package Projekt.PCCarovnik.models;

import jakarta.persistence.*;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

@Entity
public class Konfiguracija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String naziv;
    private Date datumNastanka;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uporabnik_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    Uporabnik uporabnik;

    @ManyToMany(cascade = CascadeType.MERGE, mappedBy = "konfiguracije",fetch = FetchType.EAGER)
    private Collection<Komponenta> komponente = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Date getdatumNastanka() {
        return datumNastanka;
    }

    public void setdatumNastanka() {
        this.datumNastanka = new Date();
    }

    public Uporabnik getUporabnik() {
        return uporabnik;
    }

    public void setUporabnik(Uporabnik uporabnik) {
        this.uporabnik = uporabnik;
    }

    public Collection<Komponenta> getKomponente() {
        return komponente;
    }

    public void addKomponenta(Komponenta komponenta) {
        this.komponente.add(komponenta);
        komponenta.getKonfiguracije().add(this);
    }

    public void removeKomponenta(Komponenta komponenta) {
        this.komponente.remove(komponenta);
        komponenta.getKonfiguracije().remove(this);
    }

}
