package com.codev.recruitment.lib.dagger

import android.content.Context
import androidx.room.Room
import com.codev.recruitment.lib.BuildConfig
import com.codev.recruitment.lib.room.daos.ContactDao
import com.codev.recruitment.lib.room.databases.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideContactDao(appDatabase: AppDatabase): ContactDao {
        return appDatabase.contactDao()
    }

    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {

        val passphrase = SQLiteDatabase.getBytes(BuildConfig.PASSPHRASE.toCharArray())
        return Room.databaseBuilder(appContext, AppDatabase::class.java, "app_database")
            .openHelperFactory(SupportFactory(passphrase))
            .build()
    }
}