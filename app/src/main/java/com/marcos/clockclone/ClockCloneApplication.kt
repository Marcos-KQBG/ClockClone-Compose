package com.marcos.clockclone

import android.app.Application
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration

class ClockCloneApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(this)
        )
        Configuration.getInstance().userAgentValue = packageName
    }
}