package com.beautysalon.demo.service;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.repository.ServizioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServizioService {

    @Autowired
    private ServizioRepository servizioRepository;


    public Servizio findById(Long id) {
        return servizioRepository.findById(id).orElse(null);
    }


    public List<Servizio> findAll() {
        return servizioRepository.findAll();
    }

    @Transactional
    public void delete(Servizio servizio) {
        this.servizioRepository.delete(servizio);
    }

    public boolean alreadyExists(Servizio target) {
        return this.servizioRepository.existsByNomeAndProfessionista(target.getNome(), target.getProfessionista());
    }

    public List<Professionista> getProfessionistiByServizio(Long servizioId) {
        Servizio servizio = findById(servizioId);
        return List.of(servizio.getProfessionista()); // Assuming Servizio has a method getProfessionista()
    }

    @Transactional
    public void update(Servizio servizio, Servizio newServizio) {
        servizio.setNome(newServizio.getNome());
        servizio.setPrezzo(newServizio.getPrezzo());
        this.servizioRepository.save(servizio);
    }

    @Transactional
    public void save(Servizio servizio) {
        this.servizioRepository.save(servizio);
    }

    public List<Servizio> findByProfessionistaId(Long professionistaId) {
        return servizioRepository.findByProfessionistaId(professionistaId);
    }

}
