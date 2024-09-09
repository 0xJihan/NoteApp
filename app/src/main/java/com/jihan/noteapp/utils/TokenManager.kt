package com.jihan.noteapp.utils

import android.content.Context
import com.jihan.noteapp.utils.Constants.TOKEN
import com.jihan.noteapp.utils.Constants.sharedPrefs
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext val  context: Context) {

    val prefs = context.getSharedPreferences(sharedPrefs,Context.MODE_PRIVATE)


    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken() : String? {
        return prefs.getString(TOKEN, null)
    }

}