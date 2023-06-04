package com.rodrigo.contactmanager.dto;

import com.rodrigo.contactmanager.dto.ContactsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.Link;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    private ContactsDTO contactsDTO;
    private Link externalLink;

}
