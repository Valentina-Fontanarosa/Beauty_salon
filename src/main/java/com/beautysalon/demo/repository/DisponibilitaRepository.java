package com.beautysalon.demo.repository;

import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DisponibilitaRepository extends CrudRepository<Disponibilita, Long> {

    public boolean existsByDataAndOraInizioAndOraFineAndProfessionista(String data, String oraInizio, String oraFine, Professionista professionista);

    List<Disponibilita> findAll();

    public List<Disponibilita> findByProfessionistaAndActiveTrueOrderByDataAscOraInizio(Professionista professionista);

}
