package com.zoomerbox

import android.app.Application
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.zoomerbox.di.AppComponent
import com.zoomerbox.di.DaggerAppComponent
import com.zoomerbox.di.module.AppModule

class ZoomerboxApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(this, FirebaseAuth.getInstance()))
            .build()
    }

    companion object {

        fun getAppComponent(context: Context): AppComponent {
            return (context.applicationContext as ZoomerboxApplication).appComponent
        }
    }
}