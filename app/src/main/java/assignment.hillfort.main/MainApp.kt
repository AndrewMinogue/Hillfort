package assignment.hillfort.main

import android.app.Application
import assignment.hillfort.models.HillfortJSONStore
import assignment.hillfort.models.HillfortStore
import assignment.hillfort.models.UserJSONStore
import assignment.hillfort.models.UserStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        hillforts =  HillfortJSONStore(applicationContext)
        users =  UserJSONStore(applicationContext)
        info("Hillfort started")
    }
}