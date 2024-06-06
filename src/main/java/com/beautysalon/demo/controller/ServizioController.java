package com.beautysalon.demo.controller;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.service.ProfessionistaService;
import com.beautysalon.demo.service.ServizioService;
import com.beautysalon.demo.utility.FileStore;
import com.beautysalon.demo.validation.ServizioValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.beautysalon.demo.model.Professionista.DIR_ADMIN_PAGES_PROF;
import static com.beautysalon.demo.model.Servizio.*;

@Controller
public class ServizioController {

    @Autowired
    private ServizioService servizioService;

    @Autowired
    private ProfessionistaService professionistaService;

    @Autowired
    private ServizioValidator servizioValidator;

    @GetMapping("/servizio/{id}")
    public String getServizio(@PathVariable("id") Long id, Model model) {
        Servizio servizio = servizioService.findById(id);
        List<Professionista> professionisti = servizioService.getProfessionistiByServizio(id);
        model.addAttribute("servizio", servizio);
        model.addAttribute("professionisti", professionisti);
        return Servizio.DIR_PAGES_SERVIZIO + "servizio";
    }

    @GetMapping("/servizi")
    public String getServizi(Model model) {
        model.addAttribute("servizi", this.servizioService.findAll());

        return DIR_PAGES_SERVIZIO + "servizi";
    }

    /* METHODS ADMIN */


    @GetMapping("/admin/servizi")
    public String getServiziOfProfessionista( Model model) {
        model.addAttribute("servizi", this.servizioService.findAll());
        return DIR_ADMIN_PAGES_SERVIZIO + "adminServizio";
    }


    // --- INSERIMENTO ---/

    @GetMapping("/admin/servizio/add/{id}")
    public String selezionaServizio(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("servizio", new Servizio());

        return DIR_ADMIN_PAGES_SERVIZIO + "formServizio";
    }

    @PostMapping("/admin/servizio/add/{id}")
    public String addServizio(@Valid @ModelAttribute("servizio") Servizio servizio,
                              BindingResult bindingResult,
                              @PathVariable("id") Long id,
                              @RequestParam("file") MultipartFile file,
                              Model model) {

        Professionista professionista = professionistaService.findById(id);
        servizio.setProfessionista(professionista);
        this.servizioValidator.validate(servizio, bindingResult);
        if(!bindingResult.hasErrors()) {
            servizio.setImg(FileStore.store(file,DIR_FOLDER_IMG));
            this.professionistaService.addServizio(professionista, servizio);

            return "redirect:/admin/servizi";
        }

        model.addAttribute("id", id);
        return DIR_ADMIN_PAGES_SERVIZIO + "formServizio";
    }

    // --- ELIMINAZIONE

    @GetMapping("/admin/servizio/delete/{id}")
    public String deleteServizio(@PathVariable("id") Long id, Model model) {
        Servizio servizio = this.servizioService.findById(id);
        Professionista professionista = this.professionistaService.findById(servizio.getProfessionista().getId());
        professionista.getServizi().remove(servizio);
        this.servizioService.delete(servizio);
        this.professionistaService.save(professionista);

        return "redirect:/admin/servizi";
    }

    // --- MODIFICA

    @GetMapping("/admin/servizio/edit/{id}")
    public String getEditServizio(@PathVariable("id") Long id, Model model) {
        Servizio servizio = this.servizioService.findById(id);
        model.addAttribute("servizio", servizio);

        return DIR_ADMIN_PAGES_SERVIZIO + "modificaServizio";
    }

    @PostMapping("/admin/servizio/edit/{id}")
    public String editServizio(@Valid @ModelAttribute("servizio") Servizio servizio,
                               BindingResult bindingResult, @PathVariable("id") Long id,
                               Model model) {

        Servizio s = this.servizioService.findById(id);
        servizio.setProfessionista(s.getProfessionista());

        if(servizio.getNome().equals(s.getNome())) {
            servizio.setNome("nomeSerDef");
            this.servizioValidator.validate(servizio, bindingResult);
            servizio.setNome(s.getNome());
        }else {
            this.servizioValidator.validate(servizio, bindingResult);
        }

        servizio.setId(id);
        if(!bindingResult.hasErrors()) {
            this.servizioService.update(s, servizio);

            return "redirect:/admin/servizi";
        }
        servizio.setImg(s.getImg());
        return DIR_ADMIN_PAGES_SERVIZIO + "modificaServizio";
    }

    @PostMapping("/admin/servizio/changeImg/{idS}")
    public String changeImgChef(@PathVariable("idS") Long idS,
                                @RequestParam("file") MultipartFile file,
                                Model model) {

        Servizio s = this.servizioService.findById(idS);
        if(!s.getImg().equals("profili")) {
            FileStore.removeImg(DIR_FOLDER_IMG, s.getImg());
        }

        s.setImg(FileStore.store(file, DIR_FOLDER_IMG));
        this.servizioService.save(s);
        return this.getEditServizio(idS, model);
    }


}
