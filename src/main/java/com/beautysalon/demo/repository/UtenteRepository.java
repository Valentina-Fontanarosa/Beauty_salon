package com.beautysalon.demo.repository;

import com.beautysalon.demo.model.Utente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteRepository extends CrudRepository<Utente, Long>{
    boolean existsByNomeAndCognome(String nome, String cognome);
    List<Utente> findAll();
}