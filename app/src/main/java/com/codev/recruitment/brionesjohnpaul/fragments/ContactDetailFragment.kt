package com.codev.recruitment.brionesjohnpaul.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.codev.recruitment.brionesjohnpaul.R
import com.codev.recruitment.brionesjohnpaul.databinding.FragmentContactDetailBinding
import com.codev.recruitment.brionesjohnpaul.models.StatusMessage
import com.codev.recruitment.brionesjohnpaul.viewmodels.ContactDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactDetailFragment : BaseFragment(), MenuProvider {

    private lateinit var binding: FragmentContactDetailBinding
    private val contactDetailViewModel: ContactDetailViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = contactDetailViewModel

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun setUpView() {
        binding.buttonSave.setOnClickListener {
            if (contactDetailViewModel.isAdd.value == true) {
                contactDetailViewModel.addContact()
            } else {
                contactDetailViewModel.updateContact()
            }

        }
    }

    override fun observeViewModel() {
        observe(contactDetailViewModel.status) {
            if (it.message.isNotBlank()) {
                showDialog(it)
            }
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        if (contactDetailViewModel.isAdd.value == false) menuInflater.inflate(
            R.menu.menu_contact_detail,
            menu
        )
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_delete -> {
                contactDetailViewModel.deleteContact()
                return true
            }
            else -> false
        }
    }

    private fun showDialog(statusMessage: StatusMessage) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Contacts")
        builder.setMessage(statusMessage.message)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { _, _ ->
            run {
                if (statusMessage.isSuccess) {
                    findNavController().navigate(R.id.action_ContactDetailFragment_to_ContactListFragment)
                }
            }
        })
        builder.show()
    }
}