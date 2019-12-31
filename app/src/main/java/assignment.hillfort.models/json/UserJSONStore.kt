package assignment.hillfort.models.json

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import assignment.hillfort.helpers.*
import assignment.hillfort.models.UserModel
import assignment.hillfort.models.UserStore
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


    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.password = user.password
            foundUser.loggedIn = user.loggedIn
            serialize()
        }
    }

    override fun create(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    private fun serialize() {
        val jsonString = user_gsonBuilder.toJson(users,
            user_listType
        )
        write(context, user_JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, user_JSON_FILE)
        users = Gson().fromJson(jsonString, user_listType)
    }
}