package com.codev.recruitment.lib.room.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.codev.recruitment.lib.room.models.Contact

@Dao
interface ContactDao {

    @Insert
    suspend fun addContact(contact: Contact)

    @Update
    suspend fun updateContact(contact: Contact)

    @Query("DELETE FROM contacts WHERE id = :contactId")
    suspend fun deleteContact(contactId: Int)

    @Query("SELECT * from contacts WHERE id = :contactId")
    suspend fun getContact(contactId: Int): Contact

    @Query("SELECT * from contacts ORDER BY favorites DESC")
    suspend fun getContacts(): List<Contact>


}