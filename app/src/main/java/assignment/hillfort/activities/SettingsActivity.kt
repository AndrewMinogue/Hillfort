package assignment.hillfort.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.UserModel
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.toolbarAdd
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SettingsActivity : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")



       var allUsers= app.users.findAll()

        for(x in allUsers)
            if(x.loggedIn == true) {
                etLogin_username1.setText(x.username)
                etLogin_password1.setText(x.password)
            }



//
//        //stats for total number of hillforts
//        var hillfortNumber: Long = 0
//        var allHillforts= app.hillforts.findAll()
//
//        for(x in allHillforts)
//            if(x.title.isNotEmpty()) {
//                hillfortNumber++
//                var hillfortNumber12= hillfortNumber.toString()
//                hillfortCount1.setText(hillfortNumber12)
//            }
//
//        //stats for total number of hillforts visited
//        var visitedNumber: Long = 0
//
//        for(x in allHillforts)
//        if(x.visited == true){
//            visitedNumber++
//            var hillfortNumberVisited= visitedNumber.toString()
//            visitedCount1.setText(hillfortNumberVisited)
//
//        }
//
//
//


    }






}