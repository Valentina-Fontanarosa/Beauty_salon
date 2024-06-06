package com.beautysalon.demo.service;

import com.beautysalon.demo.model.Prenotazione;
import com.beautysalon.demo.model.Utente;
import com.beautysalon.demo.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional
    public Utente save(Utente utente) {
        return this.utenteRepository.save(utente);
    }

    public Utente getUser(Long id) {
        Optional<Utente> result = this.utenteRepository.findById(id);
        return result.orElse(null);
    }

    @Transactional
    public Utente saveUser(Utente user) {
        return this.utenteRepository.save(user);
    }

    public Utente findById(Long id) {
        return this.utenteRepository.findById(id).get();
    }

    @Transactional
    public void update(Utente user, Long id) {
        Utente c = this.utenteRepository.findById(id).get();
        c.setNome(user.getNome());
        c.setCognome(user.getCognome());
        this.utenteRepository.save(c);
    }

    @Transactional
    public void addPrenotazione(Utente u, Prenotazione prenotazione) {
        u.getPrenotazioni().add(prenotazione);
        this.utenteRepository.save(u);
    }

    @Transactional
    public void deletePrenotazione(Utente u, Prenotazione prenotazione) {
        u.getPrenotazioni().remove(prenotazione);
        this.utenteRepository.save(u);
    }

    public List<Utente> findAll() {
        return utenteRepository.findAll();
    }


}
