package com.beautysalon.demo.repository;

import com.beautysalon.demo.model.*;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Long> {

   List<Prenotazione> findAll();

    public boolean existsByProfessionistaAndServizioAndDisponibilitaAndCliente(Professionista professionista, Servizio servizio, Disponibilita disponibilita, Utente cliente);
}
