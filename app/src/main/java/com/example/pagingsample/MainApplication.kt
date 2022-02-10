package com.example.pagingsample

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }

}