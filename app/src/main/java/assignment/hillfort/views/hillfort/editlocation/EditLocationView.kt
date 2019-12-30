package assignment.hillfort.views.hillfort.editlocation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import assignment.hillfort.R
import assignment.hillfort.models.Location
import assignment.hillfort.views.hillfort.editlocation.EditLocationPresenter
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditLocationView : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener, GoogleMap.OnMarkerClickListener, AnkoLogger {

    private lateinit var Map: GoogleMap
    var location = Location()
    lateinit var presenter: EditLocationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        presenter = EditLocationPresenter(this)
        location = intent.extras?.getParcelable<Location>("location")!!
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        toolbarAdds.title = title
        setSupportActionBar(toolbarAdds)
        info("Hillfort Activity started..")
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Map = googleMap
        Map.setOnMarkerDragListener(this)
        val loc = LatLng(location.lat, location.lng)
        val options = MarkerOptions()
            .title("Hillfort")
            .snippet("GPS : " + loc.toString())
            .draggable(true)
            .position(loc)
        Map.addMarker(options)
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, location.zoom))
        Map.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doUpdateMarker(marker)
        return false
    }

    override fun onMarkerDragStart(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude, Map.cameraPosition.zoom)
        presenter.doUpdateMarker(marker)
    }

    override fun onMarkerDrag(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude, Map.cameraPosition.zoom)
        presenter.doUpdateMarker(marker)
    }

    override fun onMarkerDragEnd(marker: Marker) {
        presenter.doUpdateLocation(marker.position.latitude, marker.position.longitude, Map.cameraPosition.zoom)
    }

    override fun onBackPressed() {
        presenter.doOnBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_map, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_up -> {
                presenter.doOnBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


}

