package com.rodrigo.contactmanager.repositories;

import com.rodrigo.contactmanager.models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {

    Optional<Contacts> findByTelephone(String telephone);

}
