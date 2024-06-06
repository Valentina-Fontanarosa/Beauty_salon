package com.beautysalon.demo.service;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.repository.ProfessionistaRepository;
import com.beautysalon.demo.repository.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessionistaService {

    @Autowired
    private ProfessionistaRepository professionistaRepository;

    @Autowired
    private ServizioRepository servizioRepository;

    @Transactional
    public void addServizio(Professionista professionista, Servizio servizio) {
        professionista.getServizi().add(servizio);
        this.professionistaRepository.save(professionista);
    }

    public List<Professionista> findAll() {
        return (List<Professionista>) professionistaRepository.findAll();
    }

    public Professionista findById(Long id) {
        return this.professionistaRepository.findById(id).get();
    }

    public boolean alreadyExists(Professionista professionista) {
        return professionistaRepository.existsByNomeAndCognomeAndProfessione(
                professionista.getNome(),
                professionista.getCognome(),
                professionista.getProfessione()
        );
    }

    @Transactional
    public void save(Professionista professionista) {
        professionistaRepository.save(professionista);
    }

    @Transactional
    public void delete(Professionista professionista) {
        this.professionistaRepository.delete(professionista);
    }


    @Transactional
    public void update(Professionista professionista, Long id) {
        Professionista p = this.professionistaRepository.findById(id).get();
        p.setNome(professionista.getNome());
        p.setCognome(professionista.getCognome());
        p.setProfessione(professionista.getProfessione());
        this.professionistaRepository.save(p);
    }

    @Transactional
    public void addDisponibilita(Professionista professionista, Disponibilita disponibilita) {
        professionista.getDisponibilita().add(disponibilita);
        this.professionistaRepository.save(professionista);
    }



}
