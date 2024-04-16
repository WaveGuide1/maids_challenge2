package io.barth.sms.serviceImp;

import io.barth.sms.entity.Address;
import io.barth.sms.entity.Client;
import io.barth.sms.repository.AddressRepository;
import io.barth.sms.repository.ClientRepository;
import io.barth.sms.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImp implements ClientService {

    private final ClientRepository clientRepository;

    private final AddressRepository addressRepository;


    public ClientServiceImp(ClientRepository clientRepository, AddressRepository addressRepository) {
        this.clientRepository = clientRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Client createClient(Client client) {
        Address address = client.getAddress();
        address = addressRepository.save(address);

        client.setAddress(address);
        client.setCreatedDate(LocalDateTime.now());
        return clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(Long id, Client client) {

        Client existingClient = clientRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Client with the id: "+ id + " not found"));
        existingClient.setName(client.getName());
        existingClient.setEmail(client.getEmail());
        existingClient.setLastModified(LocalDateTime.now());

        if(client.getAddress() != null){
            Address newAddress = client.getAddress();
            Address existingAddress = existingClient.getAddress();

            existingAddress.setHouseNumber(newAddress.getHouseNumber());
            existingAddress.setStreetName(newAddress.getStreetName());
            existingAddress.setZipCode(newAddress.getZipCode());
            addressRepository.save(existingAddress);
        }
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
                .orElseThrow(() -> new EntityNotFoundException("No client with id of " + id));

        if(client.getAddress() != null){
            Address address = client.getAddress();
            addressRepository.delete(address);
        }

        clientRepository.delete(client);
    }
}
