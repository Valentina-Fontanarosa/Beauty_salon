package com.beautysalon.demo.service;

import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.repository.DisponibilitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DisponibilitaService {

    @Autowired
    private DisponibilitaRepository disponibilitaRepository;

    public boolean alreadyExists(Disponibilita target) {
        return this.disponibilitaRepository.existsByDataAndOraInizioAndOraFineAndProfessionista(target.getData(), target.getOraInizio(), target.getOraFine(), target.getProfessionista());
    }

    @Transactional
    public void delete(Disponibilita disponibilita) {
        this.disponibilitaRepository.delete(disponibilita);
    }

    @Transactional
    public void update(Disponibilita disponibilita, Disponibilita newDisponibilita) {
        disponibilita.setData(newDisponibilita.getData());
        disponibilita.setOraInizio(newDisponibilita.getOraInizio());
        disponibilita.setOraFine(newDisponibilita.getOraFine());
        this.disponibilitaRepository.save(disponibilita);
    }

    public Disponibilita findById(Long idDisponibilita) {
        return this.disponibilitaRepository.findById(idDisponibilita).get();
    }
    public List<Disponibilita> findAll() {
        return disponibilitaRepository.findAll();
    }

    public List<Disponibilita> findByProfAndActive(Professionista professionista) {
        return this.disponibilitaRepository.findByProfessionistaAndActiveTrueOrderByDataAscOraInizio(professionista);
    }
}
