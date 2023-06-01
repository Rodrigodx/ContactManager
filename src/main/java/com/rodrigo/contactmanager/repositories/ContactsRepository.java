package com.rodrigo.contactmanager.repositories;

import com.rodrigo.contactmanager.models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactsRepository extends JpaRepository<Contacts, Long> {
}
