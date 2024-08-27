package com.example.task.project.customexceptions;

public class WorkExists extends Exception{
    public WorkExists(String message){
        super(message);
    }
}
