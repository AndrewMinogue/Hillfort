package assignment.hillfort.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.UserModel
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort.toolbarAdd
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class RegisterActivity : AppCompatActivity(), AnkoLogger {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        info("Register Activity started..")

    }
}