package assignment.hillfort.views.hillfort.hillfortlist

import assignment.hillfort.R
import assignment.hillfort.models.HillfortModel
import assignment.hillfort.views.hillfort.base.BasePresenter
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.base.VIEW
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_hillfort_list.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class HillfortListPresenter(view: BaseView) : BasePresenter(view) {


    private var currentHillforts: List<HillfortModel> = arrayListOf()
    private var currentHillforts1: MutableList<HillfortModel> = arrayListOf()


    fun doAddHillfort() {
        view?.navigateTo(VIEW.HILLFORT)
    }

    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun doShowHillfortsMap() {
        view?.navigateTo(VIEW.MAPS)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                currentHillforts = hillforts
                view?.showHillforts(hillforts)
            }
        }
    }

    fun doSortFavourite() {
        val favourites = app.hillforts.sortedByFavourite()
        if (favourites != null) {
            currentHillforts = favourites
            view?.showHillforts(favourites)
        }
    }

    fun doSearchHillforts() {
        currentHillforts1.clear()
        var hillfort = app.hillforts.hillfortSearch(R.id.searchET.toString())
        if (hillfort != null) {
            currentHillforts1.add(hillfort)
            view?.showHillforts(currentHillforts1)
        }
    }

    fun doLogout() {
        FirebaseAuth.getInstance().signOut()
        app.hillforts.clear()
        view?.navigateTo(VIEW.LOGIN)
    }
}