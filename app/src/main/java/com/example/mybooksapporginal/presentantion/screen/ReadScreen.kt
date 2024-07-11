package com.example.mybooksapporginal.presentantion.screen

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.data.local.pref.MyShared
import com.example.mybooksapporginal.databinding.ScreenReadBinding
import com.example.mybooksapporginal.presentantion.viewmodel.ReedScreenViewModule
import com.example.mybooksapporginal.presentantion.viewmodel.impl.ReedScreenViewModuleImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ReadScreen : Fragment(R.layout.screen_read) {
    private val binding by viewBinding(ScreenReadBinding::bind)
    private val navArgs: ReadScreenArgs by navArgs()
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private val viewModule: ReedScreenViewModule by viewModels<ReedScreenViewModuleImpl>()
    private var pathNumber = 0

    @Inject
    lateinit var myShared: MyShared
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val file = File(navArgs.bookPath)
        val docId = navArgs.docIc

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
        viewModule.getBookPath(docId)

        viewModule.setBookPath.onEach {
            pathNumber = it
        }.launchIn(lifecycleScope)

        binding.pdfView.fromFile(file)
            .defaultPage(myShared.getBookPage(key = docId))
            .onPageChange { page, _ ->
                viewModule.setBookPath(docId, page)
            }
            .enableDoubletap(true)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .load();

    }
}