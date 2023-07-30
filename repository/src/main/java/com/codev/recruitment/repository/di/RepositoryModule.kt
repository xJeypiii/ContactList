package com.codev.recruitment.repository.di

import com.codev.recruitment.repository.ContactRepository
import com.codev.recruitment.repository.ContactRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesContactRepository(impl: ContactRepositoryImpl): ContactRepository

}