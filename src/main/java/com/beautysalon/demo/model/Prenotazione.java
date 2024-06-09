package com.beautysalon.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
@Entity
public class Prenotazione {

    public static final String DIR_PAGES_PREN_USER = "genericUser/prenotazione/";
    public static final String DIR_PAGES_PREN_ADMIN = "admin/prenotazione/";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Professionista professionista;

    @ManyToOne
    private Servizio servizio;

    @OneToOne
    private Disponibilita disponibilita;

    @ManyToOne
    private Utente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Professionista getProfessionista() {
        return professionista;
    }

    public void setProfessionista(Professionista professionista) {
        this.professionista = professionista;
    }

    public Servizio getServizio() {
        return servizio;
    }

    public void setServizio(Servizio servizio) {
        this.servizio = servizio;
    }

    public Disponibilita getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(Disponibilita disponibilita) {
        this.disponibilita = disponibilita;
    }

    public Utente getCliente() {
        return cliente;
    }

    public void setCliente(Utente cliente) {
        this.cliente = cliente;
    }

}