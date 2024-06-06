package com.beautysalon.demo.repository;

import com.beautysalon.demo.model.Professionista;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProfessionistaRepository extends CrudRepository<Professionista, Long> {

    List<Professionista> findTop3ByOrderByIdDesc();

    List<Professionista> findAll();

    boolean existsByNomeAndCognomeAndProfessione(String nome, String cognome, String professione);


}
