package Projekt.PCCarovnik.services;

import Projekt.PCCarovnik.models.Komponenta;
import Projekt.PCCarovnik.models.Konfiguracija;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

@Service
public class PrimerjavaKomponentService {

    public ArrayList<String> PrimerjajKomponente(Komponenta komponenta1, Komponenta komponenta2) {

        ArrayList<String> razlikaprocent = new ArrayList<String>();

        Integer k1cena = komponenta1.getCena();
        Integer k2cena = komponenta2.getCena();
        Integer k1v1 = komponenta1.getVrednost1();
        Integer k1v2 = komponenta1.getVrednost2();
        Integer k1v3 = komponenta1.getVrednost3();
        Integer k2v1 = komponenta2.getVrednost1();
        Integer k2v2 = komponenta2.getVrednost2();
        Integer k2v3 = komponenta2.getVrednost3();

        // ce je vrednost razlike enaka -9999 potem je bla vsaj ena od zacetnih
        // vrednosti null
        Integer razlikacena = -9999;
        Integer razlika1 = -9999;
        Integer razlika2 = -9999;
        Integer razlika3 = -9999;

        if (k1cena != null && k2cena != null) {
            razlikacena = (k1cena - k2cena) / k2cena * 100;
        }
        if (k1v1 != null && k2v1 != null) {
            razlika1 = (k1v1 - k2v1) / k2v1 * 100;
        }
        if (k1v2 != null && k2v2 != null) {
            razlika2 = (k1v2 - k2v2) / k2v2 * 100;
        }
        if (k1v3 != null && k2v3 != null) {
            razlika3 = (k1v3 - k2v3) / k2v3 * 100;
        }

        String name1 = komponenta1.getNaziv();
        String name2 = komponenta2.getNaziv();

        razlikaprocent.add(name1);
        razlikaprocent.add(name2);

        razlikaprocent.add(Integer.toString(razlikacena));
        razlikaprocent.add(Integer.toString(razlika1));
        razlikaprocent.add(Integer.toString(razlika2));
        razlikaprocent.add(Integer.toString(razlika3));

        return razlikaprocent;

    }

    public ArrayList<String> PrimerjajKonfiguraciji(Konfiguracija konfiguracija1, Konfiguracija konfiguracija2) {

        ArrayList<String> razlikaKomponent = new ArrayList<String>();

        System.out.println(konfiguracija1.getKomponente());

        return razlikaKomponent;
    }

}
