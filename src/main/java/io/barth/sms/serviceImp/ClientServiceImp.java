package io.barth.sms.serviceImp;

import io.barth.sms.entity.Address;
import io.barth.sms.entity.Client;
import io.barth.sms.repository.AddressRepository;
import io.barth.sms.repository.ClientRepository;
import io.barth.sms.service.ClientService;
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
    public Client updateClient(Client client) {

        client.setLastModified(LocalDateTime.now());
        return clientRepository.save(client);
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
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
