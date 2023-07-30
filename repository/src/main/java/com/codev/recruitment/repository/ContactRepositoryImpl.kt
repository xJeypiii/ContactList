package com.codev.recruitment.repository

import com.codev.recruitment.lib.room.daos.ContactDao
import com.codev.recruitment.lib.room.models.Contact
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(private val contactDao: ContactDao) :
    ContactRepository {
    override suspend fun addContact(contact: Contact) {
        contactDao.addContact(contact)
    }

    override suspend fun updateContact(contact: Contact) {
        contactDao.updateContact(contact)
    }

    override suspend fun deleteContact(contactId: Int) {
        contactDao.deleteContact(contactId)
    }

    override suspend fun getContact(contactId: Int): Contact {
        return contactDao.getContact(contactId)
    }

    override suspend fun getContacts(): List<Contact> {
        return contactDao.getContacts()
    }
}