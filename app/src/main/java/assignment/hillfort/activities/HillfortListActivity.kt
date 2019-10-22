package assignment.hillfort.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.card_placemark.view.*
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.HillfortModel
import org.jetbrains.anko.startActivityForResult

class HillfortListActivity : AppCompatActivity() {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort_list)
        app = application as MainApp

        toolbar.title = title
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = HillfortAdapter(app.hillforts)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_add -> startActivityForResult<HillfortActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>) :
    RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_placemark,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = hillforts[holder.adapterPosition]
        holder.bind(placemark)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(placemark: HillfortModel) {
            itemView.hillfortTitle.text = placemark.title
            itemView.description.text = placemark.description
        }
    }
}



