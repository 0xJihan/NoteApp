package com.jihan.noteapp.utils

import android.text.TextUtils
import android.util.Patterns

class HelperClass {

    fun validateUserCredentials(
        userName: String = "Default",
        email: String,
        password: String
    ): Pair<Boolean, String> {

        var result = Pair(true, "")

        if (userName.isEmpty() || email.isEmpty() || password.isEmpty())  {
            return Pair(false,"Please Provide All Required Information")
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Pair(false, "Invalid Email Address")
        }
        else if (password.length < 8) {
            return Pair(false, "Password should be at least 8 characters long")
        }


        return result
    }

}