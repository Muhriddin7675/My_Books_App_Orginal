package com.example.mybooksapporginal.presentantion.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.ScreenInfoBookBinding
import com.example.mybooksapporginal.presentantion.viewmodel.BookInfoViewModule
import com.example.mybooksapporginal.presentantion.viewmodel.impl.BookInfoViewModuleImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookInfo : Fragment(R.layout.screen_info_book) {
    private val binding by viewBinding(ScreenInfoBookBinding::bind)
    private val viewModule: BookInfoViewModule by viewModels<BookInfoViewModuleImpl>()
    private val navArgs: BookInfoArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = navArgs.bookData

        binding.apply {
            bookAuthor.text = data.bookAuthor
            bookName.text = data.bookName
            bookDescription.text = data.bookDescription
        }
        if (data.bookImage != "null") {
            Glide.with(binding.root)
                .load(data.bookImage)
                .into(binding.bookImage)
        }


        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}