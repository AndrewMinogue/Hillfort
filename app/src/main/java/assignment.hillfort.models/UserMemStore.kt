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
        users.add(user)
        logAll()
    }

    fun logAll() {
        users.forEach { info("${it}") }
    }
}
