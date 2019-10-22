package assignment.hillfort.main

import android.app.Application
import assignment.hillfort.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()

    override fun onCreate() {
        super.onCreate()
        info("Hillfort started")
//        hillforts.add(HillfortModel("One", "About one..."))
//        hillforts.add(HillfortModel("Two", "About two..."))
//        hillforts.add(HillfortModel("Three", "About three..."))
    }
}