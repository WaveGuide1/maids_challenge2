package io.barth.sms.client;

import io.barth.sms.exception.ClientNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;



    public ClientServiceImp(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional
    public Client createClient(Client client) {
        client.setCreatedDate(LocalDateTime.now());
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {

        Client existingClient = clientRepository.findById(id)
                        .orElseThrow(() -> new ClientNotFoundException("Client with the given id not found"));
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setLastModified(LocalDateTime.now());

        return clientRepository.save(existingClient);
    }

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteClient(Long id) {

        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException("No client with the given id"));

        clientRepository.delete(client);
    }
}
