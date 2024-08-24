package com.example.task.project.services;

import com.example.task.project.customexceptions.InvalidClientDataException;
import com.example.task.project.entity.Client;
import com.example.task.project.repository.ClientRepository;
import org.apache.catalina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    public boolean saveNewUser(Client client) throws InvalidClientDataException {
           if (!StringUtils.hasText(client.getUserName()) || !StringUtils.hasText(client.getPassword()) ){
               throw new InvalidClientDataException("Username and password must not be null or empty");
           }
           try {
               clientRepository.save(client);
               return true;
           }catch (DataAccessException e){
               throw new RuntimeException("Database error occurred while saving the client", e);
           }
    }
}
