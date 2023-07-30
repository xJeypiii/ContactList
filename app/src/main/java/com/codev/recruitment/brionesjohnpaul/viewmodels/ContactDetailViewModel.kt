package com.codev.recruitment.brionesjohnpaul.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codev.recruitment.brionesjohnpaul.models.StatusMessage
import com.codev.recruitment.lib.room.models.Contact
import com.codev.recruitment.repository.ContactRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val repository: ContactRepository) :
    ViewModel() {

    val id = MutableLiveData<Int>()
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val phoneNumber = MutableLiveData<String>()
    val favorites = MutableLiveData<Boolean>()

    private val _status = MutableLiveData<StatusMessage>()
    val status: LiveData<StatusMessage> = _status

    private val _isAdd = MutableLiveData(true)
    val isAdd: LiveData<Boolean> = _isAdd

    val isLoading = MutableLiveData(false)

    fun setupAddView() {
        viewModelScope.launch {
            id.value = 0
            firstName.value = ""
            lastName.value = ""
            phoneNumber.value = ""
            favorites.value = false

            isLoading.value = false
            _status.value = StatusMessage()
            _isAdd.value = true
        }
    }

    fun setupEditView(contact: Contact) {
        viewModelScope.launch {
            id.value = contact.id
            firstName.value = contact.firstName
            lastName.value = contact.lastName
            phoneNumber.value = contact.phoneNumber
            favorites.value = contact.favorites

            isLoading.value = false
            _status.value = StatusMessage()
            _isAdd.value = false
        }
    }

    fun addContact() {
        viewModelScope.launch {
            isLoading.value = true
            if (isValid()) {
                val newContact = Contact(
                    0,
                    firstName.value!!,
                    lastName.value!!,
                    phoneNumber.value!!,
                    favorites.value!!
                )
                repository.addContact(newContact)
                isLoading.value = false
                _status.value = StatusMessage(true, "New contact has been saved.")
            } else {
                isLoading.value = false
                _status.value = StatusMessage(false, "Please check your inputs.")
            }
        }
    }

    fun updateContact() {
        viewModelScope.launch {
            isLoading.value = true
            if (isValid()) {
                val newContact = Contact(
                    id.value!!,
                    firstName.value!!,
                    lastName.value!!,
                    phoneNumber.value!!,
                    favorites.value!!
                )
                repository.updateContact(newContact)
                isLoading.value = false
                _status.value = StatusMessage(true, "Contact has been updated.")
            } else {
                isLoading.value = false
                _status.value = StatusMessage(false, "Please check your inputs.")
            }
        }
    }

    fun deleteContact() {
        viewModelScope.launch {
            isLoading.value = true
            id.value?.let {
                repository.deleteContact(it)
                isLoading.value = false
                _status.value = StatusMessage(true, "Contact has been deleted.")
            }
        }
    }

    private fun isValid(): Boolean {
        if (id.value == null)
            return false
        if (firstName.value.isNullOrBlank())
            return false
        if (lastName.value.isNullOrBlank())
            return false
        if (phoneNumber.value.isNullOrBlank())
            return false

        return true
    }

}