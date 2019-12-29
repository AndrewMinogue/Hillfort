package assignment.hillfort.activities

import android.content.Intent
import assignment.hillfort.helpers.showImagePicker
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.Location
import org.jetbrains.anko.intentFor


class HillfortPresenter(val view: HillfortActivity) {

    val IMAGE_REQUEST = 1
    val IMAGE_REQUEST1 = 2
    val IMAGE_REQUEST2 = 3
    val IMAGE_REQUEST3 = 4
    val LOCATION_REQUEST = 2

    var hillfort = HillfortModel()
    var location = Location(52.245696, -7.139102, 15f)
    var app: MainApp
    var edit = false;

    init {
        app = view.application as MainApp
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        }
    }

    fun doAddOrSave(title: String, description: String, additionalnotes : String) {
        hillfort.title = title
        hillfort.description = description
        hillfort.notes = additionalnotes
        if (edit) {
            app.hillforts.update(hillfort)
        } else {
            app.hillforts.create(hillfort)
        }
        view.finish()
    }

    fun doCancel() {
        view.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view.finish()
    }

    fun doSelectImage() {
        showImagePicker(view, IMAGE_REQUEST)
    }
    fun doSelectImage1() {
        showImagePicker(view, IMAGE_REQUEST1)
    }
    fun doSelectImage2() {
        showImagePicker(view, IMAGE_REQUEST2)
    }
    fun doSelectImage3() {
        showImagePicker(view, IMAGE_REQUEST3)
    }

    fun doSetLocation() {
        if (hillfort.zoom != 0f) {
            location.lat = hillfort.lat
            location.lng = hillfort.lng
            location.zoom = hillfort.zoom
        }
        view.startActivityForResult(view.intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
    }

    fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view.showHillfort(hillfort)
            }
            IMAGE_REQUEST1 -> {
                hillfort.image1 = data.data.toString()
                view.showHillfort(hillfort)
            }
            IMAGE_REQUEST2 -> {
                hillfort.image2 = data.data.toString()
                view.showHillfort(hillfort)
            }
            IMAGE_REQUEST3 -> {
                hillfort.image3 = data.data.toString()
                view.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
            }
        }
    }
}