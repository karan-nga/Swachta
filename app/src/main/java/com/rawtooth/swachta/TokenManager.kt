package com.rawtooth.swachta

import android.content.Context
import com.rawtooth.swachta.Constant.PREFS_TOKEN_FILE
import com.rawtooth.swachta.Constant.USER_TOKEN

class TokenManager(context: Context) {
    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()

    }
    fun getToken():String?{
        return prefs.getString(USER_TOKEN,null)
    }
}