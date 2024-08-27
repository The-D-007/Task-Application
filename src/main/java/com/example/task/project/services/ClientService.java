package com.example.task.project.services;

import com.example.task.project.customexceptions.InvalidDataException;
import com.example.task.project.customexceptions.UserAlreadyExists;
import com.example.task.project.entity.Client;
import com.example.task.project.entity.Task;
import com.example.task.project.pojo.ClientPojo;
import com.example.task.project.repository.ClientRepository;
import com.example.task.project.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TaskRepository taskRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public boolean saveNewUser(Client client) throws UserAlreadyExists, DataAccessException {
        Client userExist = clientRepository.findByUserName(client.getUserName());
        if (userExist != null) {
            throw new UserAlreadyExists("Username is already taken");
        } else {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            client.setRoles(Collections.singletonList("USER"));
            clientRepository.save(client);
            return true;
        }
    }
    
    public boolean login(Client client) throws InvalidDataException, DataAccessException {
        Client one = clientRepository.findByUserName(client.getUserName());

        if (passwordEncoder.matches(client.getPassword(), one.getPassword())) {
            return true;
        }
        throw new InvalidDataException("Provide correct username and password");
    }

    public void completeTask(String userName, String taskId) throws InvalidDataException{
        Client userNameExist = clientRepository.findByUserName(userName);
        Task taskIdExist = taskRepository.findByTaskId(taskId);
        if (userNameExist == null){
            throw new InvalidDataException("User does not exists.");
        }
        if (taskIdExist == null){
            throw new InvalidDataException("Task does not exists.");
        }

        if (!userNameExist.getTasks().contains(taskIdExist)){
            throw new InvalidDataException("User does not have this task.");
        }
        List<Task> completedTask = userNameExist.getCompletedTasks();
        completedTask.add(taskIdExist);
        userNameExist.setCompletedTasks(completedTask);
        userNameExist.getTasks().remove(taskIdExist);
        userNameExist.setUserAvailable(true);
        taskIdExist.setTaskCompleted(true);
        clientRepository.save(userNameExist);
        taskRepository.save(taskIdExist);
    }

    public List<ClientPojo> getAllClients(){
        List<Client> clients = clientRepository.findAll();
        List<ClientPojo> clientPojoList = new ArrayList<>();
        for (Client client : clients) {
            ClientPojo clientDTO = new ClientPojo(
                    client.getUserName(),
                    client.getEmail(),
                    client.getRoles(),
                    client.getTasks(),
                    client.getCompletedTasks(),
                    client.isUserAvailable()
            );
            clientPojoList.add(clientDTO);
        }
        return clientPojoList;
    }

    public List<ClientPojo> getAvailableClients(){
        List<ClientPojo> clients = getAllClients();
        List<ClientPojo> clientsAvailable = new ArrayList<>();
        for(ClientPojo client: clients){
            if (client.isUserAvailable()){
                clientsAvailable.add(client);
            }
        }
        return clientsAvailable;
    }
    

}
