package assignment.hillfort.main

import android.app.Application
import assignment.hillfort.models.HillfortJSONStore
import assignment.hillfort.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts =  HillfortJSONStore(applicationContext)
        info("Hillfort started")
    }
}