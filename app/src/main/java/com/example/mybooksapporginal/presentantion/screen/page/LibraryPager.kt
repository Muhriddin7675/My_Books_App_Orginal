package com.example.mybooksapporginal.presentantion.screen.page

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.PagerLibraryBinding
import com.example.mybooksapporginal.presentantion.adapter.LibraryPagerAuthorAdapter
import com.example.mybooksapporginal.presentantion.adapter.LibraryPagerInnerAdapter
import com.example.mybooksapporginal.presentantion.adapter.MenuScreenAdapter
import com.example.mybooksapporginal.presentantion.screen.MenuScreenDirections
import com.example.mybooksapporginal.presentantion.viewmodel.LibraryPagerViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.LibraryPagerViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class LibraryPager : Fragment(R.layout.pager_library) {
    private val binding by viewBinding(PagerLibraryBinding::bind)
    private val viewModel: LibraryPagerViewModel by viewModels<LibraryPagerViewModelImpl>()
    private val adapter by lazy { LibraryPagerAuthorAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvCategoryBooks.adapter = adapter
        binding.rvCategoryBooks.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnClickAll {
            findNavController().navigate(
                MenuScreenDirections.actionMenuScreenToByCategoryAllBooksScreen(it)
            )
        }
        adapter.setOnClickBookItem {
            findNavController().navigate(MenuScreenDirections.actionMenuScreenToBookInfo(it))
        }
        viewModel.loadLibraryData()
        viewModel.progress
            .onEach {
                if (it) {
                    binding.frameProgress.isVisible = true
                    binding.progressBar.show()
                } else {
                    binding.frameProgress.isVisible = false
                    binding.progressBar.hide()
                }
            }
            .launchIn(lifecycleScope)

        viewModel.errorMessage
            .onEach { Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show() }
            .launchIn(lifecycleScope)

        viewModel.loadData
            .onEach { adapter.submitList(it) }
            .launchIn(lifecycleScope)
    }
}