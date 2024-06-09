package com.beautysalon.demo.restController;

import com.beautysalon.demo.model.Prenotazione;
import com.beautysalon.demo.service.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PrenotazioneRestController {

    @Autowired
    private PrenotazioneService prenotazioneService;


    @GetMapping(value = "/rest/prenotazione/{id}")
    public Prenotazione getPrenotazione(@PathVariable("id") Long id) {
        return this.prenotazioneService.findById(id);
    }

    @GetMapping(value = "/rest/prenotazioni")
    public List<Prenotazione> getPrenotazioni() {
        return this.prenotazioneService.getAllPrenotazioni();
    }
}
