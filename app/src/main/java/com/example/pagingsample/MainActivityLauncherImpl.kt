package com.example.pagingsample

import android.content.Context
import android.content.Intent
import com.example.paging2sample.MainActivityLauncher
import javax.inject.Inject

class MainActivityLauncherImpl @Inject constructor() : MainActivityLauncher {
    override fun launch(context: Context) {
        context.startActivity(Intent(context, MainActivity::class.java))
    }
}