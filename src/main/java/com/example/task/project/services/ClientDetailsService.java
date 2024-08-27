package com.example.task.project.services;

import com.example.task.project.repository.ClientRepository;
import com.example.task.project.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Client client = clientRepository.findByUserName(name);
        if (client != null){
            return org.springframework.security.core.userdetails.User.builder()
                    .username(client.getUserName())
                    .password(client.getPassword())
                    .roles(client.getRoles().toArray(new String[0]))
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + name);
    }
}
