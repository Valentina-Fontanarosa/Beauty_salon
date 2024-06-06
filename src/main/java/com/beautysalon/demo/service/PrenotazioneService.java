package com.beautysalon.demo.service;

import com.beautysalon.demo.model.Prenotazione;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.repository.PrenotazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    public boolean alreadyExists(Prenotazione target) {
        return this.prenotazioneRepository.existsByProfessionistaAndServizioAndDisponibilitaAndCliente(target.getProfessionista(), target.getServizio(), target.getDisponibilita(), target.getCliente());
    }

    public Prenotazione findById(Long id) {
        return this.prenotazioneRepository.findById(id).get();
    }

    public void delete(Prenotazione p) {
        this.prenotazioneRepository.delete(p);
    }

    public List<Prenotazione> getAllPrenotazioni() {
        return this.prenotazioneRepository.findAll();
    }

    @Transactional
    public void save(Prenotazione prenotazione) {
        this.prenotazioneRepository.save(prenotazione);
    }
}
