package io.barth.sms.client;

import io.barth.sms.exception.GeneralApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private final ClientServiceImp clientServiceImp;

    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClient(){
        try {
            var clients = clientServiceImp.getClients();
            return new ResponseEntity<>(clients, HttpStatus.OK);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Client> createClient(@RequestBody Client client, Principal connectedUser){
        try {
            Client newClient = clientServiceImp.createClient(client, connectedUser);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id,
                                               @RequestBody Client client,
                                               Principal connectedUser){

        Client updatedClient = clientServiceImp.updateClient(id, client, connectedUser);
        return new ResponseEntity<>(updatedClient, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id, Principal connectedUser){
        Client client = clientServiceImp.getClientById(id, connectedUser);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id, Principal connectedUser){
        try {
            clientServiceImp.deleteClient(id, connectedUser);
            return ResponseEntity.noContent().build();
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }
}
