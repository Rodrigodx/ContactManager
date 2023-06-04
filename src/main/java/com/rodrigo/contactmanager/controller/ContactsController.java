package com.rodrigo.contactmanager.controller;

import com.rodrigo.contactmanager.dto.ContactResponse;
import com.rodrigo.contactmanager.dto.GetContactsDTO;
import com.rodrigo.contactmanager.models.Contacts;
import com.rodrigo.contactmanager.services.ContactsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/contacts")
@RequiredArgsConstructor
public class ContactsController {

    private final ContactsServices contactsServices;

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
        return new ResponseEntity<>(contactsServices.save(contacts), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ContactResponse> updateContacts(@RequestBody Contacts contacts, @PathVariable Long id){
        contacts.setId(id);
        return new ResponseEntity<>(contactsServices.save(contacts), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id){
        contactsServices.delete(id);
        return ResponseEntity.noContent().build();
    }

}
