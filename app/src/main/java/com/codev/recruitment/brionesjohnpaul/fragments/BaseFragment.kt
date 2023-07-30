package com.codev.recruitment.brionesjohnpaul.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment : Fragment() {

    open fun <T> Fragment.observe(flow: LiveData<T>, observer: Observer<T>) = flow.observe(viewLifecycleOwner, observer)

    open fun <T> Fragment.collect(latest: Boolean, flow: Flow<T>, collect: suspend (T) -> Unit) =
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collectLatest(collect)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        observeViewModel()
    }

    open fun setUpView() {}
    open fun observeViewModel() {}

}