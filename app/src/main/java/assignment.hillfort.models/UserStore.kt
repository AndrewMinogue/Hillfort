package assignment.hillfort.models

interface UserStore {
    fun findAll(): List<UserModel>
    fun create(user: UserModel)
}