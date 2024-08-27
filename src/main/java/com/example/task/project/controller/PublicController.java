package com.example.task.project.controller;

import com.example.task.project.customexceptions.InvalidDataException;
import com.example.task.project.customexceptions.UserAlreadyExists;
import com.example.task.project.datacheck.ClientDataCheck;
import com.example.task.project.entity.Client;
import com.example.task.project.pojo.ClientPojo;
import com.example.task.project.services.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/")
public class PublicController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientDataCheck clientDataCheck;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck(){
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody ClientPojo clientPojo){
        try {
            Client client = clientDataCheck.checkClientSignupData(clientPojo);
            boolean userSaved = clientService.saveNewUser(client);
            if (userSaved) {
                return new ResponseEntity<>("User is Created ", HttpStatus.OK);
            }
        }catch (InvalidDataException e) {
            log.error("Invalid client data: ", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (UserAlreadyExists e){
            log.error("Not available", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.CONFLICT);
        }catch (RuntimeException e) {
            log.error("Error in creating user: ", e);
        }
        return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ClientPojo clientPojo) {
        try {
            Client client = clientDataCheck.checkClientLoginData(clientPojo);
            clientService.login(client);
            return new ResponseEntity<>("User login", HttpStatus.OK);
        } catch (InvalidDataException e) {
            log.error("Invalid client data: ", e);
            return new ResponseEntity<>(String.valueOf(e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            log.error("Error in login user: ", e);
        }
        return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
