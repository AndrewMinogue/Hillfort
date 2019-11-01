package assignment.hillfort.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import assignment.hillfort.main.MainApp
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import assignment.hillfort.R
import assignment.hillfort.helpers.readImage
import assignment.hillfort.helpers.readImageFromPath
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.helpers.showImagePicker
import assignment.hillfort.models.Location
import kotlinx.android.synthetic.main.activity_hillfort.description
import kotlinx.android.synthetic.main.activity_hillfort.hillfortTitle
import kotlinx.android.synthetic.main.card_placemark.*
import org.jetbrains.anko.intentFor


class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1

    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            btnAdd.setText(R.string.save_hillfort)
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            if (hillfort.image != null) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
        }

        //Button Functionality
        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            if (hillfort.title.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                if (edit) {
                    app.hillforts.update(hillfort.copy())
                } else {
                    app.hillforts.create(hillfort.copy())
                }
            }
            info("add Button Pressed: $hillfortTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }


        //Button Functionality
        btnDelete.setOnClickListener() {
            app.hillforts.delete(hillfort)
            setResult(AppCompatActivity.RESULT_OK)
            info("Delete Button Pressed: $hillfortTitle")
            finish()
        }

        hillfortLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat =  hillfort.lat
                location.lng =  hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }


        chooseImage.setOnClickListener {
            info ("Select image")
            showImagePicker(this, IMAGE_REQUEST)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    hillfort.image = data.getData().toString()
                    hillfortImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_hillfort_image)
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}