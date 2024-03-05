package com.example.mybooksapporginal.presentantion.screen.page

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.mybooksapporginal.R
import com.example.mybooksapporginal.databinding.PagerIntroBinding

class IntroPager:Fragment(R.layout.pager_intro) {
private val binding by  viewBinding(PagerIntroBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = requireArguments().getInt("Pos",0)

        when(position){
            0 ->{
                binding.apply {
                    introImage.setImageResource(R.drawable.image_intro_first)
                    intrTitle.setText( R.string.elektron_kitoblar)
                    introText.setText(R.string.intro_text_first)
                }
            }
            2 ->{
                binding.apply {
                    introImage.setImageResource(R.drawable.image_intro_second)
                    intrTitle.setText( R.string.audio_kitoblar)
                    introText.setText(R.string.intro_text_second)
                }
            }
            else ->{
                binding.apply {
                    introImage.setImageResource(R.drawable.image_intor_tread)
                    intrTitle.setText( R.string.cheksiz_bilim)
                    introText.setText(R.string.intro_text_tread)
                }
            }
        }

    }
}