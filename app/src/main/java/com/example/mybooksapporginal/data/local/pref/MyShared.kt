package com.example.mybooksapporginal.data.local.pref

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyShared @Inject constructor(
    private val pref: SharedPreferences
) {
    fun setHasIntroPage( number: Int) {
        pref.edit().putInt("INTRO", number).apply()
    }
    fun getHasIntroPage() = pref.getInt("INTRO", 0)


}