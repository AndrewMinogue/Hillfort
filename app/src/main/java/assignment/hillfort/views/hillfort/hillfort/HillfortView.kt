package assignment.hillfort.views.hillfort.hillfort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import assignment.hillfort.R
import assignment.hillfort.helpers.readImageFromPath
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.Location
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.hillfortlist.HillfortListView
import assignment.hillfort.views.hillfort.login.LoginView
import com.bumptech.glide.Glide
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import java.time.LocalDateTime


class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()
    lateinit var map: GoogleMap
    var  hillfortTitleStr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        super.init(toolbarAdd, true);

        presenter = initPresenter (HillfortPresenter(this)) as HillfortPresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }
        chooseImage1.setOnClickListener { presenter.doSelectImage1() }
        chooseImage2.setOnClickListener { presenter.doSelectImage2() }
        chooseImage3.setOnClickListener { presenter.doSelectImage3() }

        hillfortLocation.setOnClickListener { presenter.doSetLocation() }

        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            map = it
            presenter.doConfigureMap(map)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        hillfortTitleStr = hillfortTitle.text.toString()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        hillfortTitle.setText(hillfortTitleStr)
        mapView.onResume()
        presenter.doResartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }


    override fun showHillfort(hillfort: HillfortModel) {
        hillfortTitle.setText(hillfort.title)
        description.setText(hillfort.description)
        AdditonalNotes.setText(hillfort.notes)

        if(hillfort.visited == true){
            checkbox_visited.setChecked(true)
            hillfort.visited = true
            val current = LocalDateTime.now()
            val date = current.toString()
            Date.setText(date)
            hillfort.datevisited = date
        }else if(hillfort.visited == false){
            hillfort.visited = false
            checkbox_visited.setChecked(false)
        }

        if(hillfort.favourite == true){
            favourite.setChecked(true)
            hillfort.favourite = true
        }else if(hillfort.favourite == false){
            hillfort.favourite = false
            favourite.setChecked(false)
        }

        Glide.with(this).load(hillfort.image).into(hillfortImage)
        Glide.with(this).load(hillfort.image1).into(hillfortImage1)
        Glide.with(this).load(hillfort.image2).into(hillfortImage2)
        Glide.with(this).load(hillfort.image3).into(hillfortImage3)

        if (hillfort.image != null) {
            chooseImage.setText(R.string.change_hillfort_image)
        }
        if (hillfort.image1 != null) {
            chooseImage1.setText(R.string.change_hillfort_image)
        }
        if (hillfort.image2 != null) {
            chooseImage2.setText(R.string.change_hillfort_image)
        }
        if (hillfort.image3 != null) {
            chooseImage3.setText(R.string.change_hillfort_image)
        }
        this.showLocation(hillfort.location)
    }


    override fun showLocation(location: Location) {
        lat.setText("%.6f".format(location.lat))
        lng.setText("%.6f".format(location.lng))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    fun findVisitedChecked(): Boolean{
        var visited = false

        if(checkbox_visited.isChecked){
            visited = true
        }
        if(checkbox_visited.isChecked == false){
            visited = false
        }
        return visited
    }

    fun findFavouriteChecked(): Boolean{
        var favourite1 = false

        if(favourite.isChecked){
            favourite1 = true
        }
        if(favourite.isChecked == false){
            favourite1 = false
        }
        return favourite1
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
            }
            R.id.item_cancel -> {
                presenter.doCancel()
            }
            R.id.item_add1 -> {
                if (hillfortTitle.text.toString().isEmpty()) {
                    toast(R.string.enter_hillfort_title)
                } else {
                    presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString(), AdditonalNotes.text.toString(), findVisitedChecked(),findFavouriteChecked())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }
}