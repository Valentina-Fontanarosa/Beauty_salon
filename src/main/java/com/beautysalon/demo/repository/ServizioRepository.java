package com.beautysalon.demo.repository;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServizioRepository extends CrudRepository<Servizio, Long> {

    List<Servizio> findAll();

    @Query("SELECT s FROM Servizio s WHERE s.professionista.id = :professionistaId")
    List<Servizio> findByProfessionistaId(@Param("professionistaId") Long professionistaId);

    boolean existsByNomeAndProfessionista(String nome, Professionista professionista);
}
