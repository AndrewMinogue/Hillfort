package assignment.hillfort.models.mem

import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.HillfortStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class HillfortMemStore : HillfortStore, AnkoLogger {

    val hillforts = ArrayList<HillfortModel>()
    val hillfort = HillfortModel()

    override fun findAll(): List<HillfortModel> {
        return hillforts
    }


    override fun create(hillfort: HillfortModel) {
        hillfort.id = getId()
        hillforts.add(hillfort)
        logAll()
    }

    override fun findById(id:Long) : HillfortModel? {
        val foundHillfort: HillfortModel? = hillforts.find { it.id == id }
        return foundHillfort
    }

    override fun hillfortSearch(title: String): HillfortModel {
        if(title == hillfort.title) {
            return hillfort
        }
        return hillfort
    }

    override fun sortedByFavourite(): List<HillfortModel>? {
        return hillforts.sortedWith(compareBy { it.favourite }).asReversed()
    }

    override fun update(hillfort: HillfortModel) {
        var foundHillfort: HillfortModel? = hillforts.find { p -> p.id == hillfort.id }
        if (foundHillfort != null) {
            foundHillfort.title = hillfort.title
            foundHillfort.description = hillfort.description
            foundHillfort.image = hillfort.image
            foundHillfort.image1 = hillfort.image1
            foundHillfort.image2 = hillfort.image2
            foundHillfort.image3 = hillfort.image3
            foundHillfort.visited = hillfort.visited
            foundHillfort.datevisited = hillfort.datevisited
            foundHillfort.notes = hillfort.notes
            foundHillfort.location = hillfort.location
            foundHillfort.visited = hillfort.visited
            foundHillfort.favourite = hillfort.favourite
            foundHillfort.rating = hillfort.rating
            logAll()
        }
    }

    override fun delete(hillfort: HillfortModel) {
            hillforts.remove(hillfort)
    }

    fun logAll() {
        hillforts.forEach { info("${it}") }
    }

    override fun clear() {
        hillforts.clear()
    }
}
