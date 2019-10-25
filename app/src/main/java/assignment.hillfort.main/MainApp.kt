package assignment.hillfort.main

import android.app.Application
import assignment.hillfort.models.HillfortMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val hillforts = HillfortMemStore()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort started")
    }
}