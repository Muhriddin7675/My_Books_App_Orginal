package com.example.mybooksapporginal.presentantion.screen.page

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.PagerMyBooksBinding
import com.example.mybooksapporginal.presentantion.adapter.SaveBookAdapter
import com.example.mybooksapporginal.presentantion.screen.MenuScreenDirections
import com.example.mybooksapporginal.presentantion.viewmodel.MyBooksPagerViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.MyBooksPagerViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MyBooksPager : Fragment(R.layout.pager_my_books) {
    private val binding by viewBinding(PagerMyBooksBinding::bind)
    private val viewModel: MyBooksPagerViewModel by viewModels<MyBooksPagerViewModelImpl>()
    private val adapter by lazy { SaveBookAdapter() }
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCategoryBooks.adapter = adapter
        binding.rvCategoryBooks.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getLocalBooksList()
        viewModel.loadLocalBooks
            .onEach {
                binding.empty.isVisible = it.isEmpty()
                adapter.submitList(it)
            }
            .launchIn(lifecycleScope)

        adapter.setItemListener {
            navController.navigate(MenuScreenDirections.actionMenuScreenToBookInfo(it))
        }
        viewModel.progress
            .onEach {
                binding.progress.isVisible = it
            }
            .launchIn(lifecycleScope)
    }

}