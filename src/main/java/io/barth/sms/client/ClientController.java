package io.barth.sms.client;

import io.barth.sms.authentication.AuthenticationController;
import io.barth.sms.exception.ClientNotFoundException;
import io.barth.sms.exception.GeneralApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final ClientServiceImp clientServiceImp;

    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClient(){
        try {
            return new ResponseEntity<>(clientServiceImp.getClients(), HttpStatus.OK);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @PostMapping("/")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        try {
            Client newClient = clientServiceImp.createClient(client);
            return new ResponseEntity<>(newClient, HttpStatus.CREATED);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client){
        if(clientServiceImp.getClientById(id).isEmpty()){
            throw new ClientNotFoundException("No such client");
        }
        try {
            client.setId(id);
            Client updatedClient = clientServiceImp.updateClient(id, client);
            return ResponseEntity.ok(updatedClient);
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id){
        Optional<Client> client = clientServiceImp.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        try {
            clientServiceImp.deleteClient(id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex){
            throw new GeneralApplicationException("Something went wrong");
        }
    }
}
