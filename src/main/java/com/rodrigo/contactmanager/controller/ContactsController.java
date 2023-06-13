package com.rodrigo.contactmanager.controller;

import com.rodrigo.contactmanager.dto.ContactResponse;
import com.rodrigo.contactmanager.dto.GetContactsDTO;
import com.rodrigo.contactmanager.models.Contacts;
import com.rodrigo.contactmanager.repositories.ContactsRepository;
import com.rodrigo.contactmanager.services.ContactsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactsServices contactsServices;
    private final ContactsRepository contactsRepository;
    @GetMapping
    public ResponseEntity<List<EntityModel<GetContactsDTO>>> findAll(){
        return ResponseEntity.ok(contactsServices.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<ContactResponse> getContactById(@PathVariable Long id) {
        ContactResponse contactResponse = contactsServices.findById(id);
        return ResponseEntity.ok(contactResponse);
    }
    @PostMapping
    public ResponseEntity<ContactResponse> createContacts(@RequestBody Contacts contacts) {
        Optional<Contacts> existingContact = contactsRepository.findByTelephone(contacts.getTelephone());

        if(existingContact.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The contact with this number already exists");
        }
        else {
            return new ResponseEntity<>(contactsServices.save(contacts), HttpStatus.CREATED);
        }
    }
    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactResponse> updateContacts(@RequestBody Contacts contacts, @PathVariable Long id){
        if(contactsRepository.existsById(id)){
            contacts.setId(id);
            return ResponseEntity.ok(contactsServices.save(contacts));
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found contact with id: " + id);
        }
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        if(contactsRepository.existsById(id)){
            contactsServices.delete(id);
            return ResponseEntity.noContent().build();
        }
        else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found contact with id: " + id);
        }
    }
}
