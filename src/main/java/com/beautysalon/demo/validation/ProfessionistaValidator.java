package com.beautysalon.demo.validation;

import com.beautysalon.demo.model.Professionista;
import com.beautysalon.demo.service.ProfessionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProfessionistaValidator implements Validator {

    @Autowired
    private ProfessionistaService professionistaService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Professionista.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(professionistaService.alreadyExists((Professionista) target))
            errors.reject("duplicate.professionista");
    }

}