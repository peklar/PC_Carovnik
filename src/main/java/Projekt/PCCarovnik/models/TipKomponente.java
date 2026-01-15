package Projekt.PCCarovnik.models;

import jakarta.persistence.Enumerated;

import jakarta.persistence.*;

public enum TipKomponente {
    @Enumerated(EnumType.STRING)
    CPU,
    @Enumerated(EnumType.STRING)
    GPU,
    @Enumerated(EnumType.STRING)
    RAM,
    @Enumerated(EnumType.STRING)
    SSD,
    @Enumerated(EnumType.STRING)
    HDD,
    @Enumerated(EnumType.STRING)
    PSU,
    @Enumerated(EnumType.STRING)
    MOTHERBOARD,
    @Enumerated(EnumType.STRING)
    CASE
}
