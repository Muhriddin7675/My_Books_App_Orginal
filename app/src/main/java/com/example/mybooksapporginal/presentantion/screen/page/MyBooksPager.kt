package com.example.mybooksapporginal.presentantion.screen.page

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.PagerMyBooksBinding
import com.example.mybooksapporginal.presentantion.viewmodel.MyBooksPagerViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.MyBooksPagerViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBooksPager:Fragment(R.layout.pager_my_books) {
    private val binding by viewBinding(PagerMyBooksBinding::bind)
    private val viewModel :MyBooksPagerViewModel by viewModels<MyBooksPagerViewModelImpl>()

}