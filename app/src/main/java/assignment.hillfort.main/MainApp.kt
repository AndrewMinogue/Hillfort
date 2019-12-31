package assignment.hillfort.main

import android.app.Application
import assignment.hillfort.models.json.HillfortJSONStore
import assignment.hillfort.models.HillfortStore
import assignment.hillfort.models.json.UserJSONStore
import assignment.hillfort.models.UserStore
import assignment.hillfort.models.room.HillfortStoreRoom
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore
    lateinit var users: UserStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortStoreRoom(applicationContext)
        users = UserJSONStore(applicationContext)
        info("Hillfort started")
    }
}