package com.beautysalon.demo.validation;

import com.beautysalon.demo.model.Servizio;
import com.beautysalon.demo.service.ServizioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ServizioValidator implements Validator {

    @Autowired
    private ServizioService servizioService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Servizio.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(this.servizioService.alreadyExists((Servizio) target))
            errors.reject("duplicate.servizio");
    }

}
