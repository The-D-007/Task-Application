package com.example.task.project.customexceptions;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists(String message){
        super(message);
    }
}
