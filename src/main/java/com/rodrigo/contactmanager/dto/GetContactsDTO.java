package com.rodrigo.contactmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetContactsDTO {

    private String name;

    public GetContactsDTO(Long id, String name, String telephone) {
    }
}
