package com.rodrigo.contactmanager.services;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ContactsServices {

    private final ContactsRepository contactsRepository;

    @ReadOnlyProperty
    public List<GetContactsDTO> findAll(){
        List<Contacts> result = contactsRepository.findAll();
        return result.stream().map(contact -> {
            GetContactsDTO dto = new GetContactsDTO();
            dto.setName(contact.getName());
            return dto;
        }).toList();
    }

    @Transactional
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
