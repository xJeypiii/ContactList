package com.codev.recruitment.lib.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codev.recruitment.lib.room.daos.ContactDao
import com.codev.recruitment.lib.room.models.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao
}