package com.beautysalon.demo.controller;

import com.beautysalon.demo.model.Credentials;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.model.Utente;
import com.beautysalon.demo.repository.ProfessionistaRepository;
import com.beautysalon.demo.repository.ServizioRepository;
import com.beautysalon.demo.repository.UtenteRepository;
import com.beautysalon.demo.service.CredentialsService;
import com.beautysalon.demo.service.ProfessionistaService;
import com.beautysalon.demo.service.ServizioService;
import com.beautysalon.demo.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private ServizioService servizioService;

    @Autowired
    private ProfessionistaRepository professionistaRepository;

    @Autowired
    private ServizioRepository servizioRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/")
    public String getServiziAndProfessionisti(Model model) {

        // Recupera gli ultimi professionisti dal repository
        Iterable<Professionista> professionisti = professionistaRepository.findTop3ByOrderByIdDesc();
        model.addAttribute("professionisti", professionisti);
        // Recupera tutte le categorie
        Iterable<Servizio> servizi = servizioRepository.findAll();
        model.addAttribute("servizi", servizi);

        return "index";
    }
}
