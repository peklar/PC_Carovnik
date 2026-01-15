package Projekt.PCCarovnik.models;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class Uporabnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "uporabnik_vloga", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

    private List<Role> vloge = new ArrayList<>();

    @OneToMany(mappedBy = "uporabnik", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Collection<Konfiguracija> konfiguracije;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String ime;
    private String geslo;
    private String email;
    private boolean isAdmin;

    public String getIme() {
        return ime;
    }

    public String getGeslo() {
        return geslo;
    }

    public String getEmail() {
        return email;
    }
    
    public boolean getisAdmin() {
        return isAdmin;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setGeslo(String geslo) {
        this.geslo = geslo;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
