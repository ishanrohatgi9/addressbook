package com.vaadin.tutorial.addressbook.backend;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContactServiceTest {

    @Test
    void newServiceHasNoContacts() {
        ContactService service = new ContactService();

        assertEquals(0, service.count());
    }

    @Test
    void savingContactIncreasesCount() {
        ContactService service = new ContactService();
        Contact contact = new Contact();
        contact.setFirstName("Ishan");
        contact.setLastName("Rohatgi");

        service.save(contact);

        assertEquals(1, service.count());
        assertNotNull(contact.getId());
    }

    @Test
    void findAllReturnsSavedContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact();
        contact.setFirstName("Ishan");
        contact.setLastName("Rohatgi");

        service.save(contact);

        List<Contact> contacts = service.findAll(null);

        assertEquals(1, contacts.size());
        assertEquals("Ishan", contacts.get(0).getFirstName());
    }

    @Test
    void deleteRemovesContact() {
        ContactService service = new ContactService();
        Contact contact = new Contact();
        contact.setFirstName("Ishan");
        contact.setLastName("Rohatgi");

        service.save(contact);
        service.delete(contact);

        assertEquals(0, service.count());
    }

    @Test
    void filterReturnsMatchingContacts() {
        ContactService service = new ContactService();

        Contact matching = new Contact();
        matching.setFirstName("Ishan");
        matching.setLastName("Rohatgi");
        service.save(matching);

        Contact other = new Contact();
        other.setFirstName("Rahul");
        other.setLastName("Sharma");
        service.save(other);

        List<Contact> results = service.findAll("Ishan");

        assertEquals(1, results.size());
        assertEquals("Ishan", results.get(0).getFirstName());
    }
}
