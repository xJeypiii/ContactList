package com.codev.recruitment.brionesjohnpaul.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.recruitment.lib.room.models.Contact
import com.codev.recruitment.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(private val repository: ContactRepository): ViewModel() {

    private val _contactList = MutableLiveData<List<Contact>>()
    val contactList: LiveData<List<Contact>> = _contactList

    val isLoading  = MutableLiveData(false)

    fun initContactList() {
        isLoading.value = true
        viewModelScope.launch {
            _contactList.postValue(repository.getContacts())
            isLoading.value = false
        }
    }
}