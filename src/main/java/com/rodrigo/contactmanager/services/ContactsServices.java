package com.rodrigo.contactmanager.services;

import com.rodrigo.contactmanager.controller.ContactsController;
import com.rodrigo.contactmanager.dto.ContactsDTO;
import com.rodrigo.contactmanager.dto.GetContactsDTO;
import com.rodrigo.contactmanager.models.Contacts;
import com.rodrigo.contactmanager.repositories.ContactsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ContactsServices {

    private final ContactsRepository contactsRepository;

    @ReadOnlyProperty
    public List<EntityModel<GetContactsDTO>> findAll() {
        List<Contacts> result = contactsRepository.findAll();
        List<EntityModel<GetContactsDTO>> entities = new ArrayList<>();

        for (Contacts contact : result) {
            GetContactsDTO getContactsDTO = new GetContactsDTO();
            getContactsDTO.setName(contact.getName());

            Link link = WebMvcLinkBuilder.linkTo(ContactsController.class)
                    .slash(contact.getId())
                    .withRel(contact.getName());

            EntityModel<GetContactsDTO> entity = EntityModel.of(getContactsDTO, link);
            entities.add(entity);
        }

        return entities;
    }

    @ReadOnlyProperty
    public ContactsDTO findById(Long id) {
        Contacts contacts = contactsRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Contato não encontrado com o ID: " + id));

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper.map(contacts, ContactsDTO.class);
    }
    @Transactional
    public ContactsDTO save(Contacts contacts) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Contacts, ContactsDTO> contactsToPostContactsDtoConverter = new AbstractConverter<>() {
            protected ContactsDTO convert(Contacts source) {
                ContactsDTO dto = new ContactsDTO();
                dto.setName(source.getName());
                dto.setTelephone(source.getTelephone());
                dto.setId(source.getId());

                return dto;
            }
        };

        modelMapper.addConverter(contactsToPostContactsDtoConverter);

        Contacts savedContacts = contactsRepository.save(contacts);
        return modelMapper.map(savedContacts, ContactsDTO.class);
    }

    @Transactional
    public void delete(Long id){
        contactsRepository.deleteById(id);
    }
}
