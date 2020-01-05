package assignment.hillfort.views.hillfort.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import org.jetbrains.anko.startActivityForResult
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.settings.SettingsView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_hillfort_list.toolbar

class HillfortListView: BaseView(), HillfortListener {

    lateinit var presenter: HillfortListPresenter
    lateinit var app: MainApp
    var favouriteT = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        super.init(toolbar, false)
        app = application as MainApp


        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHillforts()

        searchBtn.setOnClickListener{ presenter.doSearchHillforts()}

    }


    override fun showHillforts(hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> presenter.doAddHillfort()
            R.id.item_map -> presenter.doShowHillfortsMap()
            R.id.item_logout ->presenter.doLogout()
            R.id.item_favourites -> {
                if(favouriteT) {
                    presenter.loadHillforts()
                    favouriteT = !favouriteT
                    item.setTitle(R.string.hillfort_favourites)
                } else {
                    presenter.doSortFavourite()
                    favouriteT = !favouriteT
                    item.setTitle(R.string.hillfort_showall)
                }
            }
            R.id.item_settings -> startActivityForResult<SettingsView>(0)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        presenter.loadHillforts()
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }


}



