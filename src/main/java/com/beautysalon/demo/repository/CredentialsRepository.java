package com.beautysalon.demo.repository;

import com.beautysalon.demo.model.Credentials;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {

    public Optional<Credentials> findByUsername(String username);


}
