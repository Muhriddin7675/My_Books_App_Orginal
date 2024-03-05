package com.example.mybooksapporginal.presentantion.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.ScreenCategoryByBooksAllBinding
import com.example.mybooksapporginal.presentantion.adapter.ByCategoryAllBookAdapter
import com.example.mybooksapporginal.presentantion.viewmodel.ByCategoryAllBooksViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.ByCategoryAllBooksViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ByCategoryAllBooksScreen : Fragment(R.layout.screen_category_by_books_all) {
    private val binding by viewBinding(ScreenCategoryByBooksAllBinding::bind)
    private val viewModel: ByCategoryAllBooksViewModel by viewModels<ByCategoryAllBooksViewModelImpl>()
    private val adapter by lazy { ByCategoryAllBookAdapter() }
    private val navArgs: ByCategoryAllBooksScreenArgs by navArgs()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvAllBooks.adapter = adapter
        binding.rvAllBooks.layoutManager = LinearLayoutManager(requireContext())
        adapter.submitList(navArgs.categoryData.bookList)
        adapter.setOnClickBookItem {
            navController.navigate(
                ByCategoryAllBooksScreenDirections.actionByCategoryAllBooksScreenToBookInfo(it)
            )
        }
        binding.back.setOnClickListener {
            navController.popBackStack()
        }
    }
}