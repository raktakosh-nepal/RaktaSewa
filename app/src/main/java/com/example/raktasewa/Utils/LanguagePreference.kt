package com.example.raktasewa.Utils

import android.content.Context
import android.content.SharedPreferences

class LanguagePreference(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("RaktaSewaPrefs", Context.MODE_PRIVATE)

    companion object {
        private const val LANGUAGE_KEY = "selected_language"
        const val LANGUAGE_ENGLISH = "Eng"
        const val LANGUAGE_NEPALI = "Nep"
    }

    fun saveLanguage(language: String) {
        sharedPreferences.edit().putString(LANGUAGE_KEY, language).apply()
    }

    fun getLanguage(): String {
        return sharedPreferences.getString(LANGUAGE_KEY, LANGUAGE_ENGLISH) ?: LANGUAGE_ENGLISH
    }
}
