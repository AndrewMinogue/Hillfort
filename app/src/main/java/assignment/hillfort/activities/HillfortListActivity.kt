package assignment.hillfort.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import org.jetbrains.anko.startActivityForResult
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.models.UserModel
import kotlinx.android.synthetic.main.settings.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivity

class HillfortListActivity : AppCompatActivity(),HillfortListener {

    lateinit var app: MainApp
    var user = UserModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadHillforts()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<HillfortActivity>(0)
            R.id.item_map -> startActivity<HillfortMapsActivity>()
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
        startActivityForResult(intentFor<HillfortActivity>().putExtra("hillfort_edit", hillfort), 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadHillforts()
        recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }


    //Need to use the findall() function but filter the retrieved array to only contain the hillforts that have the same user ID
    //Create a userID field in Hillforts? that will contain the id of the user when logged in ?
    private fun loadHillforts() {
        showHillforts(app.hillforts.findAll())
    }

    fun showHillforts (hillforts: List<HillfortModel>) {
        recyclerView.adapter = HillfortAdapter(hillforts, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
}



