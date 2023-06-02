package com.rodrigo.contactmanager.models;


import com.rodrigo.contactmanager.validation.ContactNumberConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "tb_contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @ContactNumberConstraint
    private String telephone;

}
