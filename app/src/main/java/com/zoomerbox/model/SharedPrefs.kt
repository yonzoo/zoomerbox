package com.zoomerbox.model

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {
    private val FILENAME = "prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(FILENAME, 0)
    private val TOKEN = "authToken"
    private val USER = "userId"

    var accessToken: String?
        get() = prefs.getString(TOKEN, "")
        set(value) = prefs.edit().putString(TOKEN, value).apply()

    var userId: String?
        get() = prefs.getString(USER, "")
        set(value) = prefs.edit().putString(USER, value).apply()
}