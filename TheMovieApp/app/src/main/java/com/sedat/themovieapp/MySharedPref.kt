package com.sedat.themovieapp

import android.content.Context

class MySharedPref(context: Context) {
    private val shared = context.getSharedPreferences("com.sedat.themovieapp", Context.MODE_PRIVATE)

    fun saveLanguage(language: String){
        shared.edit().putString("language", language).apply()
    }
    fun getLanguage(): String{
        return shared.getString("language", "en").toString()
    }
}