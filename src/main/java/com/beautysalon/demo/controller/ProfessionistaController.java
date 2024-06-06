package com.beautysalon.demo.controller;

import com.beautysalon.demo.model.Disponibilita;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.repository.ServizioRepository;
import com.beautysalon.demo.service.ProfessionistaService;
import com.beautysalon.demo.service.ServizioService;
import com.beautysalon.demo.validation.ProfessionistaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.beautysalon.demo.utility.FileStore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.beautysalon.demo.model.Professionista.*;

@Controller
public class ProfessionistaController {

    @Autowired
    private ProfessionistaService professionistaService;

    @Autowired
    private ServizioService servizioService;

    @Autowired
    private ServizioRepository servizioRepository;

    @Autowired
    private ProfessionistaValidator professionistaValidator;


    @GetMapping("/professionista/{id}")
    public String getProfessionista(@PathVariable("id") Long id, Model model) {
        Professionista professionista = professionistaService.findById(id);
        List<Servizio> servizi = servizioService.findByProfessionistaId(id);
        professionista.setServizi(servizi);
        model.addAttribute("professionista", professionista);
        return DIR_PAGES_PROF + "professionista";
    }

    @GetMapping("/professionisti")
    public String getProfessionisti(Model model) {
        model.addAttribute("professionisti", this.professionistaService.findAll());

        return DIR_PAGES_PROF + "professionisti";
    }

    /* ADMIN */

    /* elenco PROFESSIONISTI */

    @GetMapping("/admin/professionisti")
    public String getAdminProfessionisti(Model model) {
        model.addAttribute("professionisti", this.professionistaService.findAll());

        return DIR_ADMIN_PAGES_PROF + "adminProfessionista";
    }

    // --- INSERIMENTO --- //

    @GetMapping("/admin/professionista/add")
    public String addProfessionista(Model model) {
        model.addAttribute("professionista", new Professionista());

        return DIR_ADMIN_PAGES_PROF + "formProfessionista";
    }

    @PostMapping("/admin/professionista/add")
    public String addNewProfessionista(@Valid @ModelAttribute("professionista") Professionista professionista,
                                       BindingResult bindingResult,
                                       @RequestParam("file") MultipartFile file,
                                       Model model) {
        this.professionistaValidator.validate(professionista, bindingResult);

        if(!bindingResult.hasErrors()) {
            professionista.setImg(FileStore.store(file, DIR_FOLDER_IMG));
            this.professionistaService.save(professionista);

            return this.getAdminProfessionisti(model);
        }

        return DIR_ADMIN_PAGES_PROF + "formProfessionista";
    }


    // --- ELIMINAZIONE --- //

    @GetMapping("/admin/professionista/delete/{id}")
    public String deleteProfessionista(@PathVariable("id") Long id, Model model) {
        Professionista professionista = professionistaService.findById(id);
        FileStore.removeImg(DIR_FOLDER_IMG, professionista.getImg());

        //eliminazione immagini a cascata
        professionista.getServizi().stream().forEach(servizio -> servizio.eliminaImmagine());

        this.professionistaService.delete(professionista);

        return this.getAdminProfessionisti(model);
    }

    // --- MODIFICA ---//

    @GetMapping("/admin/professionista/edit/{id}")
    public String getEditProfessionista(@PathVariable("id") Long id, Model model) {
        Professionista professionista = this.professionistaService.findById(id);
        List<Servizio> servizi = professionista.getServizi(); // Ottiene i servizi del professionista
        model.addAttribute("professionista", professionista);
        model.addAttribute("servizi", servizi);// Aggiunge i servizi al modello
        List<Disponibilita> disponibilita = professionista.getDisponibilita();
        model.addAttribute("disponibilitaList", disponibilita);
        return DIR_ADMIN_PAGES_PROF + "modificaProfessionista";
    }

    @PostMapping("/admin/professionista/edit/{id}")
    public String modificaProfessionista(@Valid @ModelAttribute("professionista") Professionista professionista,
                                         BindingResult bindingResult,
                                         @PathVariable("id") Long id,
                                         Model model) {
        // Trova il professionista esistente nel database
        Professionista p = this.professionistaService.findById(id);

        // Valida l'oggetto professionista
        this.professionistaValidator.validate(professionista, bindingResult);

        if(!bindingResult.hasErrors()) {
            this.professionistaService.update(professionista, id);
            return this.getAdminProfessionisti(model);
        }
        professionista.setImg(p.getImg());

        // Ricarica i servizi nel modello in caso di errori di validazione
        model.addAttribute("servizi", p.getServizi());
        model.addAttribute("disponibilitaList", p.getDisponibilita());
        return DIR_ADMIN_PAGES_PROF + "modificaProfessionista";
    }

    @PostMapping("/admin/professionista/changeImg/{idProf}")
    public String changeImgChef(@PathVariable("idProf") Long idProf,
                                @RequestParam("file") MultipartFile file,
                                Model model) {

        Professionista p = this.professionistaService.findById(idProf);
        if(!p.getImg().equals("profili")) {
            FileStore.removeImg(DIR_FOLDER_IMG, p.getImg());
        }

        p.setImg(FileStore.store(file, DIR_FOLDER_IMG));
        this.professionistaService.save(p);
        return this.getEditProfessionista(idProf, model);
    }


}
