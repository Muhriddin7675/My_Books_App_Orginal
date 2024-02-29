package com.example.mybooksapporginal.data.local.pref

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyShared @Inject constructor(
    private val pref: SharedPreferences
) {
    fun setHasIntroPage( number: Int) {
        pref.edit().putInt("INTRO", 1).apply()
    }
    fun getHasIntroPage() = pref.getInt("INTRO", 0)

    fun setUid(uid:String){
        pref.edit().putString("UID",uid).apply()
    }
    fun getUid() = pref.getString("UID","")


}