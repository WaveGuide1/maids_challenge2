package io.barth.sms.client;

import io.barth.sms.client.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    public Client createClient(Client client);

    public Client updateClient(Long id, Client client);

    public List<Client> getClients();

    public Optional<Client> getClientById(Long id);

    public void deleteClient(Long id);
}
