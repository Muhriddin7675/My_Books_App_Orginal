package com.example.mybooksapporginal

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        changeColorStatusBar(true)
        Log.d("TTT", "onCreate: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("TTT", "onPause: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d("TTT", "onStart: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d("TTT", "onStop: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TTT", "onResume: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TTT", "onDestroy: ")
    }


    fun Activity.changeColorStatusBar(bool: Boolean, color: Int = R.color.Color_AliceBlue) {
        val window: Window = this.window
        val decorView = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)
        wic.isAppearanceLightStatusBars = bool
        window.statusBarColor = ContextCompat.getColor(this, color)
    }
}

class Frag : Fragment() {}