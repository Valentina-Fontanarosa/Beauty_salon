package com.beautysalon.demo.model;

import com.beautysalon.demo.utility.FileStore;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
public class Professionista {

    public static final String DIR_PAGES_PROF = "/informations/professionista/";
    public static final String DIR_ADMIN_PAGES_PROF = "admin/professionista/";

    public static final String DIR_FOLDER_IMG = "professionisti/profili";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    private String professione;

    private String img;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professionista")
    private List<Servizio> servizi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professionista")
    private List<Disponibilita> disponibilita;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "professionista")
    private List<Prenotazione> prenotazioni;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getProfessione() {
        return professione;
    }

    public void setProfessione(String professione) {
        this.professione = professione;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<Servizio> getServizi() {
        return servizi;
    }

    public void setServizi(List<Servizio> servizi) {
        this.servizi = servizi;
    }

    public List<Disponibilita> getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(List<Disponibilita> disponibilita) {
        this.disponibilita = disponibilita;
    }

    public List<Prenotazione> getPrenotazioni() {
        return prenotazioni;
    }

    public void setPrenotazioni(List<Prenotazione> prenotazioni) {
        this.prenotazioni = prenotazioni;
    }

    public void eliminaImmagine() {
        FileStore.removeImg(DIR_FOLDER_IMG, this.getImg());
    }

}
