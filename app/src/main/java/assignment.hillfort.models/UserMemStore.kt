package assignment.hillfort.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class UserMemStore : UserStore, AnkoLogger {

    val users = ArrayList<UserModel>()
    val user = UserModel()

    override fun findAll(): List<UserModel>{
        return users
    }

    override fun create(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        logAll()
    }

    fun logAll() {
        users.forEach { info("${it}") }
    }

    override fun update(user: UserModel) {
        var foundUser: UserModel? = users.find { p -> p.id == user.id }
        if (foundUser != null) {
            foundUser.username = user.username
            foundUser.password = user.password
            foundUser.loggedIn = user.loggedIn
            logAll()
        }
    }

}
