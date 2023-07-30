package com.codev.recruitment.repository

import com.codev.recruitment.lib.room.models.Contact

interface ContactRepository {

    suspend fun addContact(contact: Contact)

    suspend fun updateContact(contact: Contact)

    suspend fun deleteContact(contactId: Int)

    suspend fun getContact(contactId: Int): Contact

    suspend fun getContacts(): List<Contact>
}