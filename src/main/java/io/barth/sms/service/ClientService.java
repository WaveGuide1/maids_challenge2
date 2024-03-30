package io.barth.sms.service;

import io.barth.sms.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public Client createClient(Client client);

    public Client updateClient(Client client);

    public List<Client> getClients();

    public Optional<Client> getClientById(Long id);

    public void deleteClient(Long id);
}
