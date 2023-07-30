package com.codev.recruitment.brionesjohnpaul.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.codev.recruitment.brionesjohnpaul.R
import com.codev.recruitment.brionesjohnpaul.databinding.ActivityMainBinding
import com.codev.recruitment.brionesjohnpaul.viewmodels.ContactDetailViewModel
import com.codev.recruitment.brionesjohnpaul.viewmodels.ContactListViewModel
import com.codev.recruitment.lib.room.models.Contact
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    private val contactListViewModel: ContactListViewModel by viewModels()
    private val contactDetailViewModel: ContactDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.contactListViewModel = contactListViewModel
        binding.contactDetailViewModel = contactDetailViewModel

        setSupportActionBar(binding.toolbar)

        navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        contactListViewModel.initContactList()

    }

    fun setupAddView() {
        contactDetailViewModel.setupAddView()
    }

    fun setupEditView(contact: Contact) {
        contactDetailViewModel.setupEditView(contact)
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}