package io.barth.sms.client;

import io.barth.sms.client.Client;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    public Client createClient(Client client, Principal connectedUser);

    public Client updateClient(Long id, Client client, Principal connectedUser);

    public List<Client> getClients();

    public Client getClientById(Long id, Principal connectedUser);

    public void deleteClient(Long id, Principal connectedUser);
}
