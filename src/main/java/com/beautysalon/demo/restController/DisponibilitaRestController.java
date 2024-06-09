package com.beautysalon.demo.restController;

import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.model.Prenotazione;
import com.beautysalon.demo.service.DisponibilitaService;
import com.beautysalon.demo.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DisponibilitaRestController {

    @Autowired
    private DisponibilitaService disponibilitaService;


    @GetMapping(value = "/rest/disponibilita/{id}")
    public Disponibilita getDisponibilita(@PathVariable("id") Long id) {
        return this.disponibilitaService.findById(id);
    }

    @GetMapping(value = "/rest/disponibilita")
    public List<Disponibilita> getListaDisponibilita() {
        return this.disponibilitaService.findAll();
    }
}
