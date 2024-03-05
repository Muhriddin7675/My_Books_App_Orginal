package com.example.mybooksapporginal.presentantion.screen

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.data.BookData
import com.example.mybooksapporginal.data.enamdata.UploadBookData
import com.example.mybooksapporginal.databinding.ScreenInfoBookBinding
import com.example.mybooksapporginal.domain.AppRepository
import com.example.mybooksapporginal.presentantion.viewmodel.BookInfoViewModule
import com.example.mybooksapporginal.presentantion.viewmodel.impl.BookInfoViewModuleImpl
import com.example.mybooksapporginal.util.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class ScreenInfoBook : Fragment(R.layout.screen_info_book) {
    private val binding by viewBinding(ScreenInfoBookBinding::bind)
    private val viewModel: BookInfoViewModule by viewModels<BookInfoViewModuleImpl>()
    private val navArgs: ScreenInfoBookArgs by navArgs()
    private var isHasBook = false
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }
    private lateinit var dialog: Dialog
    private lateinit var data: BookData

    @Inject
    lateinit var repository: AppRepository
    private var fileTemp: File? = null

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog = Dialog(requireContext())
        val bookData = navArgs.bookData
        binding.apply {
            bookName.text = bookData.bookName
            bookAuthor.text = bookData.bookAuthor
            bookDescription.text = bookData.bookDescription
            seekbar.setOnTouchListener { v, event -> true  }
            if(bookData.bookImage != "null"){
                Glide.with(binding.root)
                    .load(bookData.bookImage)
                    .into(binding.bookImage)
            }else{
                binding.bookImage.setImageResource(R.drawable.book_image)
            }
        }

        binding.back.setOnClickListener {
            navController.popBackStack()
        }
        data = navArgs.bookData

        val bookDescription = data.bookDescription
        val bookDocID = data.docId
        val bookName = data.bookName
        val bookImage = data.bookImage
        val bookAuthor = data.bookAuthor

        viewModel.hasBookFromLocal(bookDocID)

        binding.bookAuthor.text = bookAuthor
        binding.bookName.text = bookName
        binding.bookDescription.text = bookDescription

        Glide.with(binding.root.context)
            .load(bookImage)
            .into(binding.bookImage)

        viewModel.isHasBookListener.onEach { bool ->
            isHasBook = bool
            if (isHasBook) {
                binding.btnDown.text = "O'qish"
            } else {
                binding.btnDown.text = "Yuklab Olish"
            }
        }.launchIn(lifecycleScope)



        binding.btnDown.setOnClickListener {
            if (fileTemp != null) {
                navController.navigate(
                    ScreenInfoBookDirections.actionBookInfoToReadScreen(
                      fileTemp!!.absolutePath

                    )
                )
            } else if (isHasBook) {
                "Bu book mavjud".myLog()
                viewModel.getDownloadedBook(data)
            } else {
                binding.bottomBar.visibility = View.VISIBLE
                repository.downloadBookPdf(data)
                    .onEach {
                        when (it) {
                            UploadBookData.CANCEL -> {
                                Toast.makeText(requireContext(), "CANCEL", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            UploadBookData.PAUSE -> {
                                Toast.makeText(requireContext(), "PAUSE", Toast.LENGTH_SHORT).show()
                            }

                            UploadBookData.RESUME -> {
                                Toast.makeText(requireContext(), "RESUME", Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is UploadBookData.Error -> {
                                Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                            is UploadBookData.Progress -> {
                                val uploadBytes = it.uploadBytes / 1024
                                val totalBytes = it.totalBytes / 1024

                                binding.progress.text =
                                    "${uploadBytes}/${totalBytes} MB   ${it.uploadBytes * 100 / it.totalBytes}%"
                                binding.seekbar.progress =
                                    (it.uploadBytes * 100 / it.totalBytes).toInt()
                                "${it.uploadBytes * 100 / it.totalBytes}".myLog()
                            }

                            is UploadBookData.SUCCESS -> {
                                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT)
                                    .show()
                                binding.bottomBar.visibility = View.GONE
                                binding.btnDown.text = "O'qish"
                                fileTemp = it.file

                            }
                        }
                    }
                    .launchIn(lifecycleScope)
            }
        }
        binding.cancelButton.setOnClickListener {
            binding.bottomBar.visibility = View.GONE
            repository.cancelBookUploading()
        }
        binding.playButton.setOnClickListener {
            binding.playButton.visibility = View.GONE
            binding.pauseButton.visibility = View.VISIBLE
            repository.pauseBookUploading()
        }
        binding.pauseButton.setOnClickListener {
            binding.playButton.visibility = View.VISIBLE
            binding.pauseButton.visibility = View.GONE
            repository.resumeBookUploading()
        }
        viewModel.fileLiveData.onEach {
            navController.navigate(
                ScreenInfoBookDirections.actionBookInfoToReadScreen(
                 it.path)
            )
        }.launchIn(lifecycleScope)


    }

}