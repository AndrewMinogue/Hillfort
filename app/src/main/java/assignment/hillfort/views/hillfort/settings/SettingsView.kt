package assignment.hillfort.views.hillfort.settings

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.hillfortlist.HillfortListPresenter
import assignment.hillfort.views.hillfort.hillfortlist.HillfortListView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hillfort.toolbarAdd
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.startActivityForResult


class SettingsView : BaseView(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            etLogin_username1.text = "${user.email}"
        }

        //stats for total number of hillforts
        var hillfortNumber: Long = 0
        var allHillforts= app.hillforts.findAll()

        for(x in allHillforts)
            if(x.title.isNotEmpty()) {
                hillfortNumber++
                var hillfortNumber12= hillfortNumber.toString()
                hillfortCount1.setText(hillfortNumber12)
            }

        //stats for total number of hillforts visited
        var visitedNumber: Long = 0

        for(x in allHillforts)
        if(x.visited == true){
            visitedNumber++
            var hillfortNumberVisited= visitedNumber.toString()
            visitedCount1.setText(hillfortNumberVisited)

        }
    }
}