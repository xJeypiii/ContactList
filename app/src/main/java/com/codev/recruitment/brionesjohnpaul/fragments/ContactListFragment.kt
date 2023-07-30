package com.codev.recruitment.brionesjohnpaul.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codev.recruitment.brionesjohnpaul.R
import com.codev.recruitment.brionesjohnpaul.activities.MainActivity
import com.codev.recruitment.brionesjohnpaul.adapters.ContactListAdapter
import com.codev.recruitment.brionesjohnpaul.databinding.FragmentContactListBinding
import com.codev.recruitment.brionesjohnpaul.viewmodels.ContactListViewModel
import com.codev.recruitment.lib.room.models.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactListFragment : BaseFragment(), MenuProvider {

    private lateinit var binding: FragmentContactListBinding
    private val contactListViewModel: ContactListViewModel by activityViewModels()
    private val contactList = ArrayList<Contact>()
    private lateinit var adapter: ContactListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = contactListViewModel

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun setUpView() {
        binding.contacts.layoutManager = LinearLayoutManager(context)
        adapter = ContactListAdapter(contactList, ::onClick)
        binding.contacts.adapter = adapter

        contactListViewModel.initContactList()
    }

    override fun observeViewModel() {
        observe(contactListViewModel.contactList) {
            contactList.clear()
            contactList.addAll(it)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_contact_list, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_add -> {
                (activity as MainActivity).setupAddView()
                findNavController().navigate(R.id.action_ContactListFragment_to_ContactDetailFragment)
                return true
            }
            else -> false
        }
    }

    private fun onClick(contact: Contact) {
        (activity as MainActivity).setupEditView(contact)
        findNavController().navigate(R.id.action_ContactListFragment_to_ContactDetailFragment)
    }
}