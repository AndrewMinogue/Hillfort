package assignment.hillfort.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.RequiresApi
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
import org.jetbrains.anko.intentFor
import java.time.LocalDateTime



class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    var edit = false
    val IMAGE_REQUEST = 1
    val IMAGE_REQUEST1 = 3
    val IMAGE_REQUEST2 = 4
    val IMAGE_REQUEST3 = 5

    val LOCATION_REQUEST = 2
    var location = Location(52.245696, -7.139102, 15f)


    //When using the now function for date it said i didn't have the right api version and would only work with this
    //If app is not working take this line above onCreate out, however datevisited will not work.
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        app = application as MainApp
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        //Edit Functionality For hllfort
        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            AdditonalNotes.setText(hillfort.notes)
            hillfort.notes = AdditonalNotes.text.toString()
            hillfortImage.setImageBitmap(readImageFromPath(this, hillfort.image))
            hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
            hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
            hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
            if (hillfort.image.isNotEmpty()) {
                chooseImage.setText(R.string.change_hillfort_image)
            }
            if (hillfort.image1.isNotEmpty()) {
                chooseImage1.setText(R.string.change_hillfort_image)
            }
            if (hillfort.image2.isNotEmpty()) {
                chooseImage2.setText(R.string.change_hillfort_image)
            }
            if (hillfort.image3.isNotEmpty()) {
                chooseImage3.setText(R.string.change_hillfort_image)
            }
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
            btnAdd.setText(R.string.save_hillfort)
        }

        // Button Functionality
        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            hillfort.notes = AdditonalNotes.text.toString()
            if(checkbox_visited.isChecked){
                hillfort.visited = true
                val current = LocalDateTime.now()
                val date = current.toString()
                Date.setText(date)
                hillfort.datevisited = date
            } else if(checkbox_visited.isChecked == false){
                hillfort.visited = false
            }

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

        //marker needs to be clicked again to show location.
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
        chooseImage1.setOnClickListener {
            info ("Select image")
            showImagePicker(this, IMAGE_REQUEST1)
        }
        chooseImage2.setOnClickListener {
            info ("Select image")
            showImagePicker(this, IMAGE_REQUEST2)
        }
        chooseImage3.setOnClickListener {
            info ("Select image")
            showImagePicker(this, IMAGE_REQUEST3)
        }

    }

    //When using the now function for date it said i didn't have the right api version and would only work with this
    //If app is not working take this line above onActivityResult out, however datevisited will not work.

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
            IMAGE_REQUEST1 -> {
                if (data != null) {
                    hillfort.image1 = data.getData().toString()
                    hillfortImage1.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage1.setText(R.string.change_hillfort_image)
                }
            }
            IMAGE_REQUEST2 -> {
                if (data != null) {
                    hillfort.image2 = data.getData().toString()
                    hillfortImage2.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage2.setText(R.string.change_hillfort_image)
                }
            }
            IMAGE_REQUEST3 -> {
                if (data != null) {
                    hillfort.image3 = data.getData().toString()
                    hillfortImage3.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage3.setText(R.string.change_hillfort_image)
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


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        when (item?.itemId) {
            R.id.item_delete -> {
                app.hillforts.delete(hillfort)
                setResult(AppCompatActivity.RESULT_OK)
                info("Delete Button Pressed: $hillfortTitle")
                finish()
            }
        }

        when (item?.itemId) {
            R.id.item_add1 -> {
                hillfort.title = hillfortTitle.text.toString()
                hillfort.description = description.text.toString()
                hillfort.notes = AdditonalNotes.text.toString()
                if(checkbox_visited.isChecked){
                    hillfort.visited = true
                    val current = LocalDateTime.now()
                    val date = current.toString()
                    Date.setText(date)
                    hillfort.datevisited = date
                } else if(checkbox_visited.isChecked == false){
                    hillfort.visited = false
                }


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
        }
        return super.onOptionsItemSelected(item)
    }
}