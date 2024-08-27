package com.example.task.project.customexceptions;

public class InvalidDataException extends Exception{
    public InvalidDataException(String message){
        super(message);
    }
}
