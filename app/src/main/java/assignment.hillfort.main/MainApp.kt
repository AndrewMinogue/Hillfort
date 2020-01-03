package assignment.hillfort.main

import android.app.Application
import assignment.hillfort.models.HillfortStore
import assignment.hillfort.models.firebase.HillfortFireStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortFireStore(applicationContext)
        info("Hillfort started")
    }
}