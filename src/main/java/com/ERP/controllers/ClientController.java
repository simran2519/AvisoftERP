package com.ERP.controllers;

import com.ERP.entities.Client;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.services.ClientService;
import com.ERP.utils.MyResponseGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

//    @PostMapping("/add")
//    public ResponseEntity<Client>addClient(@RequestBody Client client)
//    {
//        return  ResponseEntity.ok(clientService.addClient(client));
//    }

    @PostMapping("/add")
    public ResponseEntity<Object> addClient (@Valid @RequestBody Client client) {
        try {
            Client newClient = clientService.addClient(client);
            return MyResponseGenerator.generateResponse(HttpStatus.CREATED, true, "Client is added", newClient);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Error adding client: " + e.getMessage(), null);
        }
    }


    @PutMapping("/update/{clientId}")
    public ResponseEntity<Object> updateClient(@Valid @RequestBody Client client, @PathVariable Long clientId) {
        try {
            Client updatedClient = clientService.updateClient(client, clientId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Client is updated successfully", updatedClient);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Error updating client: " + e.getMessage(), null);
        }
    }

    @GetMapping("/find/{clientId}")
    public ResponseEntity<Object> findClient(@PathVariable long clientId) {
        try {
            Client clientToFind = clientService.findClient(clientId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Client is found", clientToFind);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND, false, "Client not found with id: " + clientId, null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Error finding client: " + e.getMessage(), null);
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<Object> findAllClients() {
        try {
            List<Client> clients = clientService.findAllClients();
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Clients are found", clients);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Error finding clients: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete/{clientId}")
    public ResponseEntity<Object> deleteClient(@PathVariable long clientId) {
        try {
            Client deletedClient = clientService.deleteClient(clientId);
            return MyResponseGenerator.generateResponse(HttpStatus.OK, true, "Client is deleted successfully", deletedClient);
        } catch (IdNotFoundException e) {
            return MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND, false, "Client not found with id: " + clientId, null);
        } catch (Exception e) {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST, false, "Error deleting client: " + e.getMessage(), null);
        }
    }



}
