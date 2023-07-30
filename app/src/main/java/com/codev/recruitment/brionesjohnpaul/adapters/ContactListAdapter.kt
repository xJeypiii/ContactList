package com.codev.recruitment.brionesjohnpaul.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.codev.recruitment.brionesjohnpaul.R
import com.codev.recruitment.brionesjohnpaul.databinding.ItemContactBinding
import com.codev.recruitment.lib.room.models.Contact

class ContactListAdapter(private val contactList: ArrayList<Contact>,
                         private val clickListener: (Contact) -> Unit): RecyclerView.Adapter<ContactViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(layoutInflater, parent, false)

        return ContactViewHolder(binding)
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
       holder.bind(contactList[position], clickListener)
    }

}

class ContactViewHolder(private val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root) {

    private var contact = Contact(0,"", "", "", false)

    fun bind(item: Contact, clickListener: (Contact) -> Unit) {
        binding.viewHolder = this
        contact = item
        binding.root.setOnClickListener {
            clickListener(item)
        }
    }

    fun getName(): String = binding.root.context.getString(R.string.format_name, contact.lastName, contact.firstName)

    fun getPhoneNumber(): String = contact.phoneNumber

    fun isFavorite(): Boolean = contact.favorites


}