package assignment.hillfort.views.hillfort.favourite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info



class FavouriteView : AppCompatActivity(), AnkoLogger {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")
    }
}
