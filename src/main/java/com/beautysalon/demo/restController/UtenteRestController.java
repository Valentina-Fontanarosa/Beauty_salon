package com.beautysalon.demo.restController;

import com.beautysalon.demo.model.Utente;
import com.beautysalon.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UtenteRestController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping(value = "/rest/utente/{id}")
    public Utente getUtente(@PathVariable("id") Long id) {
        return this.utenteService.findById(id);
    }

    @GetMapping(value = "/rest/utenti")
    public List<Utente> getUtenti() {
        return this.utenteService.findAll();
    }
}
