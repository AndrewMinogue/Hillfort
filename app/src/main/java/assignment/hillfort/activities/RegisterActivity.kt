package assignment.hillfort.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.UserModel
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_register.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity(), AnkoLogger {

    var user = UserModel()
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        info("Register Activity started..")
        app = application as MainApp
        //Register button functionality
        Register.setOnClickListener() {
            user.username = etRegister_username.text.toString()
            user.password = etRegister_password.text.toString()


            if (user.username.isEmpty() || user.password.isEmpty()) {
                toast(R.string.enter_register_detail)
            } else {
                    if (user.username.isNotEmpty() && user.password.isNotEmpty()) {
                        toast(R.string.register_successful)
                        finish()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        setResult(AppCompatActivity.RESULT_OK)
                        app.users.create(user.copy())
                    }
                }

            }


        }
    }