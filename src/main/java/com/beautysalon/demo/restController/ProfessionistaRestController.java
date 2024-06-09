package com.beautysalon.demo.restController;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Utente;
import com.beautysalon.demo.service.ProfessionistaService;
import com.beautysalon.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProfessionistaRestController {

    @Autowired
    private ProfessionistaService professionistaService;


    @GetMapping(value = "/rest/professionista/{id}")
    public Professionista getProfessionista(@PathVariable("id") Long id) {
        return this.professionistaService.findById(id);
    }

    @GetMapping(value = "/rest/professionisti")
    public List<Professionista> getProfessionisti() {
        return this.professionistaService.findAll();
    }
}
