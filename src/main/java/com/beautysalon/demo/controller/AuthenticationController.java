package com.beautysalon.demo.controller;

import com.beautysalon.demo.model.Credentials;
import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.model.Utente;
import com.beautysalon.demo.service.CredentialsService;
import com.beautysalon.demo.service.UtenteService;
import com.beautysalon.demo.validation.CredentialsValidator;
import com.beautysalon.demo.validation.UtenteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Collection;

import static com.beautysalon.demo.model.Utente.DIR_FOLDER_IMG;

@Controller
public class AuthenticationController {

    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private UtenteValidator utenteValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("credentials", new Credentials());
        return "Authentication/registerForm";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        return "Authentication/loginForm";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        return "/";
    }

    @RequestMapping(value="/default", method=RequestMethod.GET)
    public String defaultAfterLogin(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials;
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            credentials = credentialsService.getCredentials(userDetails.getUsername());
        } else if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            String email = oauth2User.getAttribute("email");
            credentials = credentialsService.getCredentials(email);

        } else {
            throw new IllegalStateException("Principal type not supported: " + principal.getClass().getName());
        }

        if (credentials != null) {
            model.addAttribute("id", credentials.getId());

            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                return "admin/dashboardAdmin";
            } else if (credentials.getRole().equals(Credentials.GENERIC_USER_ROLE)) {
                return "genericUser/dashboardGenericUser";
            }
            return this.profileUser(model);
        } else {
            model.addAttribute("message", "Spiacenti, non sei registrato");
            return "error";
        }
    }


    /* PROFILE */
    @GetMapping("/profile")
    public String profileUser(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials;
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            credentials = credentialsService.getCredentials(userDetails.getUsername());
        } else if (principal instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) principal;
            String email = oauth2User.getAttribute("email");
            credentials = credentialsService.getCredentials(email);
        } else {
            throw new IllegalStateException("Principal type not supported: " + principal.getClass().getName());
        }

        Utente user = utenteService.getUser(credentials.getUtente().getId());
        model.addAttribute("utente", user);
        model.addAttribute("credentials", credentials);
        return "Authentication/profile";
    }

    // AuthenticationController

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("utente") Utente utente,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               Model model) {

        // validazione user e credenziali
        this.utenteValidator.validate(utente, userBindingResult);

        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        if(!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            utente.setImg("profile.webp");
            credentials.setUtente(utente);


            // Salva le credenziali e gestisce eventuali errori
            Credentials savedCredentials = credentialsService.saveCredentials(credentials);
            if (savedCredentials != null) {
                return "Authentication/registrationSuccessful";
            } else {
                // Aggiungi un messaggio di errore nella pagina di registrazione
                model.addAttribute("errorMessage", "Si è verificato un problema durante il salvataggio delle credenziali.");
                return "Authentication/registerForm";
            }

        }
        return "Authentication/registerForm";
    }

    @PostMapping("/changeUserAndPass/{idCred}")
    public String changeUserAndPass(@Valid @ModelAttribute("credentials") Credentials credentials,
                                    BindingResult credentialsBindingResult,
                                    @PathVariable("idCred") Long id,
                                    @RequestParam(name = "confirmPass") String pass,
                                    Model model) {

        credentials.setUsername("defaultUsernameForVa");
        credentialsValidator.validate(credentials, credentialsBindingResult);

        if(!credentials.getPassword().equals(pass)) {
            credentialsBindingResult.addError(new ObjectError("notMatchConfirmPassword", "Le password non coincidono"));
        }

        Credentials c = credentialsService.getCredentials(id);
        Utente utente = utenteService.getUser(c.getUtente().getId());
        credentials.setUsername(c.getUsername());
        credentials.setId(id);

        if(!credentialsBindingResult.hasErrors()) {
            c.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
            credentialsService.save(c);
            model.addAttribute("utente", utente);
            model.addAttribute("credentials", c);
            model.addAttribute("okChange", true);
            return "Authentication/profile";
        }
        model.addAttribute("utente", utente);
        model.addAttribute("credentials", credentials);
        model.addAttribute("okChange", false);
        return "Authentication/profile";
    }

    @PostMapping("/changeImgProfile/{idUser}")
    public String changeImgProfile(@PathVariable("idUser") Long id,
                                   @RequestParam("file") MultipartFile file, Model model) {
        Utente utente = utenteService.getUser(id);
        if(!utente.getImg().equals("profile.webp")) {
            com.beautysalon.demo.utility.FileStore.removeImg(DIR_FOLDER_IMG, utente.getImg());
        }
        utente.setImg(com.beautysalon.demo.utility.FileStore.store(file, DIR_FOLDER_IMG));
        utenteService.saveUser(utente);
        return this.profileUser(model);
    }

    // -- MODIFICA PROFILE -- //

    @GetMapping("/modificaProfile/{idUser}")
    public String selezionaProfile(@PathVariable("idUser") Long idUser, Model model) {
        Utente user = this.utenteService.findById(idUser);
        model.addAttribute("user", user);
        return "Authentication/profile";
    }

    @PostMapping("/modificaProfile/{idUser}")
    public String modificaProfile(@Valid @ModelAttribute("user") Utente user,
                                  BindingResult bindingResult,
                                  @PathVariable("idUser") Long idUser,
                                  Model model) {
        this.utenteValidator.validate(user, bindingResult);
        user.setId(idUser);
        if (!bindingResult.hasErrors()) {
            this.utenteService.update(user, user.getId());
            return this.profileUser(model);
        }
        Utente currentUser = this.utenteService.findById(idUser);
        user.setImg(currentUser.getImg());
        model.addAttribute("user", user);
        return "Authentication/profile";
    }

}
