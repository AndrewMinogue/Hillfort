package assignment.hillfort.views.hillfort.search

import assignment.hillfort.models.HillfortModel
import assignment.hillfort.views.hillfort.base.BasePresenter
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.base.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class SearchPresenter(view: BaseView) : BasePresenter(view) {


    private var currentHillforts: List<HillfortModel> = arrayListOf()


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

}