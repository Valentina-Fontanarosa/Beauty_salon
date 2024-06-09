package com.beautysalon.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
public class Disponibilita {

    public static final String DIR_ADMIN_PAGES_DISP = "admin/disponibilita/";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String data;

    @NotBlank
    private String oraInizio;

    @NotBlank
    private String oraFine;

    private boolean active;

    @ManyToOne
    private Professionista professionista;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(String oraInizio) {
        this.oraInizio = oraInizio;
    }

    public String getOraFine() {
        return oraFine;
    }

    public void setOraFine(String oraFine) {
        this.oraFine = oraFine;
    }

    public Professionista getProfessionista() {
        return professionista;
    }

    public void setProfessionista(Professionista professionista) {
        this.professionista = professionista;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
