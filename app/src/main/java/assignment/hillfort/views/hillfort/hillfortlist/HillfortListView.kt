package assignment.hillfort.views.hillfort.hillfortlist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import assignment.hillfort.R
import assignment.hillfort.activities.*
import assignment.hillfort.main.MainApp
import org.jetbrains.anko.startActivityForResult
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.UserModel
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.hillfort.HillfortView
import org.jetbrains.anko.startActivity

class HillfortListView: BaseView(), HillfortListener {

    var user = UserModel()
    lateinit var presenter: HillfortListPresenter
    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        toolbar.title = title
        setSupportActionBar(toolbar)

        presenter = initPresenter(HillfortListPresenter(this)) as HillfortListPresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadHillforts()
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
        }
        if (item?.itemId == R.id.item_logout) {
            var allUsers= app.users.findAll()

            for(x in allUsers)
                if(x.loggedIn == true) {
                    user.username = x.username
                    user.password = x.password
                    x.loggedIn = false
                    user.loggedIn = x.loggedIn
                    app.users.update(user.copy())
                }
            startActivityForResult<LoginActivity>(0)
        }
        when (item?.itemId) {
            R.id.item_settings -> startActivityForResult<SettingsActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onHillfortClick(hillfort: HillfortModel) {
        presenter.doEditHillfort(hillfort)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadHillforts()
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
}



