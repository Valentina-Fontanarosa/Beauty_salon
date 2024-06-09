package com.beautysalon.demo.restController;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.service.ProfessionistaService;
import com.beautysalon.demo.service.ServizioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ServizioRestController {

    @Autowired
    private ServizioService servizioService;


    @GetMapping(value = "/rest/servizio/{id}")
    public Servizio getServizio(@PathVariable("id") Long id) {
        return this.servizioService.findById(id);
    }

    @GetMapping(value = "/rest/servizi")
    public List<Servizio> getServizi() {
        return this.servizioService.findAll();
    }
}
