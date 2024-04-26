package io.barth.sms.client;

import io.barth.sms.authentication.AuthenticationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/management/clients")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final ClientServiceImp clientServiceImp;

    public ClientController(ClientServiceImp clientServiceImp) {
        this.clientServiceImp = clientServiceImp;
    }

    @GetMapping("/")
    public ResponseEntity<List<Client>> getAllClient(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        logger.info("Received get request from {}", createdBy);
        return new ResponseEntity<>(clientServiceImp.getClients(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Client> createClient(@RequestBody Client client){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        Client newClient = clientServiceImp.createClient(client);
        logger.info("New Client was created by {}", createdBy);
        return new ResponseEntity<>(newClient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        if(clientServiceImp.getClientById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        client.setId(id);
        Client updatedClient = clientServiceImp.updateClient(id, client);
        logger.info("Old Client was updated by {}", createdBy);
        return ResponseEntity.ok(updatedClient);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClient(@PathVariable Long id){
        Optional<Client> client = clientServiceImp.getClientById(id);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        clientServiceImp.deleteClient(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String createdBy = authentication.getName();
        logger.info("Client was deleted by {}", createdBy);
        return ResponseEntity.noContent().build();
    }
}
