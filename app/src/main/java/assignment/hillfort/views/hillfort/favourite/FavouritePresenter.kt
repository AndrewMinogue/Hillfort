package assignment.hillfort.views.hillfort.favourite

import assignment.hillfort.models.HillfortModel
import assignment.hillfort.views.hillfort.base.BasePresenter
import assignment.hillfort.views.hillfort.base.BaseView
import assignment.hillfort.views.hillfort.base.VIEW
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class FavouritePresenter(view: BaseView) : BasePresenter(view) {


    fun doEditHillfort(hillfort: HillfortModel) {
        view?.navigateTo(VIEW.HILLFORT, 0, "hillfort_edit", hillfort)
    }

    fun loadHillforts() {
        doAsync {
            val hillforts = app.hillforts.findAll()
            uiThread {
                view?.showHillforts(hillforts)
            }
        }
    }



}