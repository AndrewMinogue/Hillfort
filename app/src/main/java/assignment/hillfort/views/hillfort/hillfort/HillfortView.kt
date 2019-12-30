package assignment.hillfort.views.hillfort.hillfort

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import assignment.hillfort.R
import assignment.hillfort.helpers.readImageFromPath
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.views.hillfort.base.BaseView
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import java.time.LocalDateTime


class HillfortView : BaseView(), AnkoLogger {

    lateinit var presenter: HillfortPresenter
    var hillfort = HillfortModel()
    lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        presenter = initPresenter (HillfortPresenter(this)) as HillfortPresenter

        chooseImage.setOnClickListener { presenter.doSelectImage() }
        chooseImage1.setOnClickListener { presenter.doSelectImage1() }
        chooseImage2.setOnClickListener { presenter.doSelectImage2() }
        chooseImage3.setOnClickListener { presenter.doSelectImage3() }

        hillfortLocation.setOnClickListener { presenter.doSetLocation() }

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
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
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

        hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
        hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
        hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
        hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        if (presenter.edit) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
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
                    presenter.doAddOrSave(hillfortTitle.text.toString(), description.text.toString(), AdditonalNotes.text.toString())
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