package com.nbtec.newfitnesstracker

import android.app.Application
import com.nbtec.newfitnesstracker.model.AppDatabase

class App: Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getDatabase(this)
    }
}