package assignment.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import assignment.hillfort.helpers.*
import java.util.*


val user_JSON_FILE = "users1.json"
val user_gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val user_listType = object : TypeToken<ArrayList<UserModel>>() {}.type

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()


    constructor (context: Context) {
        this.context = context
        if (exists(context, user_JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }


    override fun create(user: UserModel) {
        users.add(user)
        serialize()
    }

    private fun serialize() {
        val jsonString = user_gsonBuilder.toJson(users, user_listType)
        write(context, user_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, user_JSON_FILE)
        users = Gson().fromJson(jsonString, user_listType)
    }
}