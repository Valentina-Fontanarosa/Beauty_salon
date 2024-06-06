package com.beautysalon.demo.controller;

import com.beautysalon.demo.model.*;
import com.beautysalon.demo.service.*;
import com.beautysalon.demo.validation.PrenotazioneValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.beautysalon.demo.model.Prenotazione.DIR_PAGES_PREN_ADMIN;
import static com.beautysalon.demo.model.Prenotazione.DIR_PAGES_PREN_USER;

@Controller
public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private ServizioService servizioService;

    @Autowired
    private DisponibilitaService disponibilitaService;

    @Autowired
    private ProfessionistaService professionistaService;

    @Autowired
    private PrenotazioneValidator prenotazioneValidator;

    @Autowired
    private UtenteService utenteService;

    // USER //
    @GetMapping("/profile/prenotazioni/{id}")
    public String getPrenotazioni(@PathVariable("id") Long id, Model model) {
        Utente utente = this.utenteService.getUser(id);
        model.addAttribute("utente", utente);
        model.addAttribute("prenotazioni", utente.getPrenotazioni());
        model.addAttribute("id", id); // Aggiungi l'id al modello
        return DIR_PAGES_PREN_USER + "elencoPrenotazioni";
    }

    @GetMapping("/profile/prenotazione/add/{id}")
    public String addPrenotazione(@PathVariable("id") Long id, Model model) {
        model.addAttribute("servizi", this.servizioService.findAll());
        model.addAttribute("idUtente", id);
        return DIR_PAGES_PREN_USER + "elencoServiziPrenotazione";
    }

    @GetMapping("/profile/prenotazione/disponibilita/{idU}/{idS}")
    public String selectDisponibilita(@PathVariable("idU") Long idUtente,
                                      @PathVariable("idS") Long idServizio,
                                      Model model) {
        model.addAttribute("idUtente", idUtente);
        model.addAttribute("idServizio", idServizio);
        model.addAttribute("prenotazione", new Prenotazione());

        Professionista p = this.servizioService.findById(idServizio).getProfessionista();

        model.addAttribute("disponibilitaList", this.disponibilitaService.findByProfAndActive(p));


        return DIR_PAGES_PREN_USER + "elencoDisponibilitaPrenotazione";
    }

    @GetMapping("/profile/prenotazione/add/{idU}/{idS}/{idD}")
    public String addPrenotazione(@Valid @ModelAttribute("prenotazione") Prenotazione p,
                                  BindingResult bindingResult,
                                  @PathVariable("idU") Long idUtente,
                                  @PathVariable("idS") Long idServizio,
                                  @PathVariable("idD") Long idDisponibilita,
                                  Model model) {

        Utente u = this.utenteService.getUser(idUtente);
        Servizio s = this.servizioService.findById(idServizio);
        Disponibilita d = this.disponibilitaService.findById(idDisponibilita);
        Professionista prof = s.getProfessionista();
        p.setProfessionista(prof);
        p.setCliente(u);
        p.setDisponibilita(d);
        p.setServizio(s);
        d.setActive(false);

        this.prenotazioneValidator.validate(p, bindingResult);
        if(!bindingResult.hasErrors()) {
            this.utenteService.addPrenotazione(u, p);
            return "redirect:/profile/prenotazioni/" + u.getId();
        }

        return DIR_PAGES_PREN_USER + "riepilogoPrenotazione";
    }

    @GetMapping("/profile/prenotazione/delete/{id}")
    public String deletePrenotazione(@PathVariable("id") Long id, Model model) {
        Prenotazione p = this.prenotazioneService.findById(id);
        Utente u = p.getCliente();
        Disponibilita d = p.getDisponibilita();
        d.setActive(true);

        this.utenteService.deletePrenotazione(u, p);
        this.prenotazioneService.delete(p);

        return "redirect:/profile/prenotazioni/" + u.getId();
    }

    // ADMIN //

    // elenco prenotazioni //
    @GetMapping("/admin/prenotazioni")
    public String getAllPrenotazioni(Model model) {
        List<Prenotazione> prenotazioni = prenotazioneService.getAllPrenotazioni();
        model.addAttribute("prenotazioni", prenotazioni);
        return DIR_PAGES_PREN_ADMIN  + "elencoPrenotazioni";
    }

    @GetMapping("/admin/prenotazioni/{id}")
    public String deletePrenotazioneAdmin(@PathVariable Long id) {

            Prenotazione p = prenotazioneService.findById(id);
            Utente u = p.getCliente();
            Disponibilita d = p.getDisponibilita();
            d.setActive(true);

            utenteService.deletePrenotazione(u, p);
            prenotazioneService.delete(p);

        return "redirect:/admin/prenotazioni";

    }
}

