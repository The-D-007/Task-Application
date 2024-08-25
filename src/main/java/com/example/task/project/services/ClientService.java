package com.example.task.project.services;

import com.example.task.project.customexceptions.InvalidClientDataException;
import com.example.task.project.customexceptions.UserAlreadyExists;
import com.example.task.project.entity.Client;
import com.example.task.project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Optional;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean saveNewUser(Client client) throws UserAlreadyExists, DataAccessException {
        Client userExist = clientRepository.findByUserName(client.getUserName());
        if (!StringUtils.hasText(String.valueOf(userExist))) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            clientRepository.save(client);
            return true;
        }
        throw new UserAlreadyExists("Username is already taken");
    }
    
    public boolean login(Client client) throws InvalidClientDataException, DataAccessException {
        Client one = clientRepository.findByUserName(client.getUserName());

        if (passwordEncoder.matches(client.getPassword(), one.getPassword())) {
            return true;
        }
        throw new InvalidClientDataException("Provide correct username and password");
    }

    

}
