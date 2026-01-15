## Funkcionalnost 1: Dodajanje nove komponente  

**Postopek testiranja:**  
1. Na strani *Dodajanje komponent* sem izbral tip “CPU”.  
2. Vpisal sem podatke:  
   - Naziv: *Intel i7-12700K*  
   - Cena: *350*  
   - Opis: *12-jedrni procesor, 5.0GHz*  
   - Datum izdaje: *2025-11-7*  
   - URL: *https://intel.com/i7*  
3. Kliknil sem “Dodaj komponento”.

**Pričakovano:** Komponenta se doda v bazo in prikaže v seznamu.  
**Dejansko:** Komponenta se pravilno doda in je vidna na seznamu.  
**Rezultat:** Uspešno se doda

---

## Funkcionalnost 2: Filtriranje komponent  

**Postopek testiranja:**  
1. Na strani *Komponente* sem izbral tip “GPU”.  
2. Vpisal sem filtre:  
   - Min cena = 300  
   - Max cena = 700  
3. Potrdil sem filtre.

**Pričakovano:** Prikazati bi se morale samo komponente znotraj izbranega cenovnega razpona.  
**Dejansko:** Prikazane so pravilne komponente, filtriranje deluje.  
**Opomba:** Pri nalaganju je manjša zakasnitev (okoli 2 sekundi).  
**Rezultat:** Uspešno  

---

## Funkcionalnost 3: Shranjevanje konfiguracije  

**Postopek testiranja:**  
1. Na strani *Konfiguracija* sem izbral:  
   - CPU: *Ryzen 7 9800x3D*  
   - GPU: *NVIDIA RTX 4090*  
   - RAM: *Corsair 16GB DDR5*  
   - PSU: *Seasonic 650W*  
   - CASE: *NZXT H510*  
2. Vpisal sem ime: *Gaming PC 2025*.  
3. Kliknil sem “Shrani konfiguracijo”.

**Pričakovano:** Konfiguracija se shrani v bazo in se poveže z uporabnikom.  
**Dejansko:** Sistem javi “Konfiguracija uspešno shranjena”.  
**Rezultat:** Uspešno  

---

## Funkcionalnost 4: Filtriranje komponent po atributih (napaka pri `maxVrednost3`)  

**Postopek testiranja:**  
1. Na strani *Komponente* sem izbral tip “GPU”.  
2. Nastavil sem filtre:  
   - Min vrednost3 = 100  
   - Max vrednost3 = 1000  
3. Počakal, da se izvede filtriranje

**Pričakovano:**  
Na seznamu se prikažejo samo GPU komponente, kjer je atribut `vrednost3` manjši ali enak 1000.

**Dejansko:**  
Prikazane so tudi komponente, kjer je `vrednost3` večja od 1000.  
Filtriranje torej ne upošteva zgornje meje (`maxVrednost3`).

**Rezultat:** Napaka pri filtriranju atributa  

**Opomba:**  
Najverjetneje backend ne obravnava pravilno parametra `maxVrednost3` v SQL queryju.

---