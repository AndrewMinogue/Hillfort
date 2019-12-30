package assignment.hillfort.views.hillfort.hillfort

import android.content.Intent
import assignment.hillfort.helpers.checkLocationPermissions
import assignment.hillfort.helpers.isPermissionGranted
import assignment.hillfort.views.hillfort.editlocation.EditLocationView
import assignment.hillfort.helpers.showImagePicker
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.Location
import assignment.hillfort.views.hillfort.base.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.jetbrains.anko.intentFor



class HillfortPresenter(view: BaseView): BasePresenter(view) {

    val IMAGE_REQUEST = 1
    val IMAGE_REQUEST1 = 3
    val IMAGE_REQUEST2 = 4
    val IMAGE_REQUEST3 = 5
    val LOCATION_REQUEST = 2
    var map: GoogleMap? = null

    var hillfort = HillfortModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;

    init {
        if (view.intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = view.intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            view.showHillfort(hillfort)
        } else {
            if (checkLocationPermissions(view)) {
                // todo get the current location
            }
        }
    }





    fun locationUpdate(lat: Double, lng: Double) {
        hillfort.lat = lat
        hillfort.lng = lng
        hillfort.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options = MarkerOptions().title(hillfort.title).position(LatLng(hillfort.lat, hillfort.lng))
        map?.addMarker(options)
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(hillfort.lat, hillfort.lng), hillfort.zoom))
        view?.showHillfort(hillfort)
    }

    fun doConfigureMap(m: GoogleMap) {
         map = m
         locationUpdate(hillfort.lat, hillfort.lng)
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
       if (isPermissionGranted(requestCode, grantResults)) {
           // todo get the current location
       } else {
           // permissions denied, so use the default location
           locationUpdate(defaultLocation.lat, defaultLocation.lng)
       }
    }



    fun doAddOrSave(title: String, description: String, additionalnotes : String) {
        hillfort.title = title
        hillfort.description = description
        hillfort.notes = additionalnotes
        //hillfort.visited= checkbox_visited
        if (edit) {
            app.hillforts.update(hillfort)
        } else {
            app.hillforts.create(hillfort)
        }
        view?.finish()
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        app.hillforts.delete(hillfort)
        view?.finish()
    }

    fun doSelectImage() {
        showImagePicker(view!!, IMAGE_REQUEST)
    }
    fun doSelectImage1() {
        showImagePicker(view!!, IMAGE_REQUEST1)
    }
    fun doSelectImage2() {
        showImagePicker(view!!, IMAGE_REQUEST2)
    }
    fun doSelectImage3() {
        showImagePicker(view!!, IMAGE_REQUEST3)
    }

    fun doSetLocation() {
        if (edit == false) {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", defaultLocation)
        } else {
            view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(hillfort.lat, hillfort.lng, hillfort.zoom)
            )
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                hillfort.image = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST1 -> {
                hillfort.image1 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST2 -> {
                hillfort.image2 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            IMAGE_REQUEST3 -> {
                hillfort.image3 = data.data.toString()
                view?.showHillfort(hillfort)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                hillfort.lat = location.lat
                hillfort.lng = location.lng
                hillfort.zoom = location.zoom
                locationUpdate(hillfort.lat, hillfort.lng)
            }
        }
    }
}