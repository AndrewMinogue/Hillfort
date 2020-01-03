package assignment.hillfort.views.hillfort.favourite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import assignment.hillfort.R
import assignment.hillfort.main.MainApp
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.hillfort.HillfortPresenter
import assignment.hillfort.views.hillfort.hillfortlist.HillfortAdapter
import assignment.hillfort.views.hillfort.hillfortlist.HillfortListener
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_hillfort.*
import kotlinx.android.synthetic.main.activity_hillfort_list.*
import kotlinx.android.synthetic.main.content_hillfort_maps.*
import kotlinx.android.synthetic.main.favourites.*
import kotlinx.android.synthetic.main.favourites.toolbar
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class FavouriteView : BaseView(), HillfortListener {

    lateinit var presenter: FavouritePresenter
    lateinit var app: MainApp



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favourites)
        app = application as MainApp


        presenter = initPresenter (FavouritePresenter(this)) as FavouritePresenter

        val layoutManager = LinearLayoutManager(this)
        recyclerView1.layoutManager = layoutManager
        var hillforts = app.hillforts.findAll()
        var hillfort = HillfortModel()


        presenter.loadHillforts()
//        showHillfort(hillfort)
    }

    override fun showHillfort(hillfort: HillfortModel) {
//        var hillforts = app.hillforts.findAll()
//        for(hillfort in hillforts)
//            if(hillfort.favourite == true) {
//                currentTitle.text = hillfort.title
//                currentDescription.text = hillfort.description
//                Glide.with(this).load(hillfort.image).into(currentImage)
//            }
    }

    override fun onHillfortClick(hillfort: HillfortModel) {
            presenter.doEditHillfort(hillfort)
    }

}





