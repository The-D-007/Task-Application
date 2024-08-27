package com.example.task.project.datacheck;

import com.example.task.project.customexceptions.InvalidDataException;
import com.example.task.project.entity.Client;
import com.example.task.project.pojo.ClientPojo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ClientDataCheck {

    private final Client client = new Client();

    public Client checkClientSignupData(ClientPojo clientPojo) throws InvalidDataException {
        if (!StringUtils.hasText(clientPojo.getUserName()) || !StringUtils.hasText(clientPojo.getPassword()) ){
            throw new InvalidDataException("Username and password must not be null or empty");
        }
        if (!clientPojo.getEmail().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
            throw new InvalidDataException("Email address must be right");
        }
       client.setUserName(clientPojo.getUserName());
        client.setPassword(clientPojo.getPassword());
        client.setEmail(clientPojo.getEmail());
        return client;
    }

    public Client checkClientLoginData(ClientPojo clientPojo) throws InvalidDataException {
        if (!StringUtils.hasText(clientPojo.getUserName()) || !StringUtils.hasText(clientPojo.getPassword())) {
            throw new InvalidDataException("Username and password must not be null or empty");
        }
        client.setUserName(clientPojo.getUserName());
        client.setPassword(clientPojo.getPassword());
        return client;
    }
}
