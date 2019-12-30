package assignment.hillfort.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.UserModel
import assignment.hillfort.views.hillfort.hillfortlist.HillfortListView
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity(), AnkoLogger {

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        info("Login Activity started..")

        app = application as MainApp

        Login.setOnClickListener() {
            var allUsers = app.users.findAll()

            for (x in allUsers)
                if (x.username == etLogin_username.text.toString() && x.password == etLogin_password.text.toString()) {
                    user.username = x.username
                    user.password = x.password
                    x.loggedIn = true
                    user.loggedIn = x.loggedIn
                    user.loggedIn = true

                    app.users.update(user.copy())
                    val intent = Intent(this, HillfortListView::class.java)
                    startActivity(intent)
                }


            if(etLogin_username.text.toString().isEmpty() || etLogin_password.text.toString().isEmpty()){
                toast(R.string.login_unsuccessful1)
            }
        }

        loginRegister.setOnClickListener(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }

}