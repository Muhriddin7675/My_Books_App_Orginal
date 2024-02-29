package com.example.mybooksapporginal.presentantion.screen.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.PagerMyFavoriteBinding
import com.example.mybooksapporginal.presentantion.viewmodel.MyFavoritePagerViewModel
import com.example.mybooksapporginal.presentantion.viewmodel.impl.MyFavoritePagerViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFavoritePager:Fragment(R.layout.pager_my_favorite) {
    private val binding by viewBinding(PagerMyFavoriteBinding::bind)
    private val viewModel :MyFavoritePagerViewModel by viewModels<MyFavoritePagerViewModelImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}