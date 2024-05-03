package io.barth.sms.client;

import io.barth.sms.exception.ClientNotFoundException;
import io.barth.sms.exception.GeneralApplicationException;
import io.barth.sms.exception.UserNotAuthenticatedException;
import io.barth.sms.user.User;
import io.barth.sms.user.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;



    public ClientServiceImp(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Client createClient(Client client, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if(user == null)
            throw new UserNotAuthenticatedException("You need to login or register as a new user");
        client.setCreatedDate(LocalDateTime.now());
        Client newClient = clientRepository.save(client);
        user.setClient(newClient);
        userRepository.save(user);
        return newClient;
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Client existingClient = clientRepository.findById(id)
                        .orElseThrow(() -> new ClientNotFoundException("Client with the given id not found"));
        if(!user.getClient().getId().equals(existingClient.getId()))
            throw new GeneralApplicationException("You can only update your account");
        existingClient.setFirstName(client.getFirstName());
        existingClient.setLastName(client.getLastName());
        existingClient.setAddress(client.getAddress());
        existingClient.setStreetNumber(client.getStreetNumber());
        existingClient.setZipCode(client.getZipCode());
        existingClient.setLastModified(LocalDateTime.now());

        return clientRepository.save(existingClient);
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(Long id, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        var client = clientRepository.findById(id).orElseThrow(() ->
                new ClientNotFoundException("No client with given id"));

        if(!user.getClient().getId().equals(client.getId()))
            throw new GeneralApplicationException("Not allowed");
        return client;
    }

    @Override
    public void deleteClient(Long id, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("No client with the given id"));

        if(!user.getClient().getId().equals(client.getId()))
            throw new GeneralApplicationException("You can only delete your account");

        clientRepository.delete(client);
    }
}
