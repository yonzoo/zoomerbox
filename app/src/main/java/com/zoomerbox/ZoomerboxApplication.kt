package com.zoomerbox

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.di.AppComponent
import com.zoomerbox.di.DaggerAppComponent
import com.zoomerbox.di.module.ApiModule
import com.zoomerbox.di.module.AppModule
import com.zoomerbox.model.SharedPrefs

class ZoomerboxApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        preferences = SharedPrefs(applicationContext)
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this, FirebaseAuth.getInstance()))
            .build()
    }

    companion object {

        lateinit var preferences: SharedPrefs

        fun getAppComponent(context: Context): AppComponent {
            return (context.applicationContext as ZoomerboxApplication).appComponent
        }
    }
}