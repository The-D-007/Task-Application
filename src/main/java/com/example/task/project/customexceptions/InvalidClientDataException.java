package com.example.task.project.customexceptions;

public class InvalidClientDataException extends Exception{
    public InvalidClientDataException(String message){
        super(message);
    }
}
