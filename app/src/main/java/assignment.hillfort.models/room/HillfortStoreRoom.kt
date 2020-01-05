package assignment.hillfort.models.room

import android.content.Context
import androidx.room.Room
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.HillfortStore

class HillfortStoreRoom(val context: Context) : HillfortStore {
    override fun hillfortSearch(title: String): HillfortModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    var dao: HillfortDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
            .fallbackToDestructiveMigration()
            .build()
        dao = database.hillfortDao()
    }

    override fun findAll(): List<HillfortModel> {
        return dao.findAll()
    }

    override fun findById(id: Long): HillfortModel? {
        return dao.findById(id)
    }

    override fun create(hillfort: HillfortModel) {
        dao.create(hillfort)
    }

    override fun update(hillfort: HillfortModel) {
        dao.update(hillfort)
    }

    override fun delete(hillfort: HillfortModel) {
        dao.deleteHillfort(hillfort)
    }

    override fun clear() {
    }

    override fun sortedByFavourite(): List<HillfortModel>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}