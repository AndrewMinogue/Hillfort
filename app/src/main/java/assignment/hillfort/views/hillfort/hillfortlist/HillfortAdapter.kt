package assignment.hillfort.views.hillfort.hillfortlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_placemark.view.*
import assignment.hillfort.R
import assignment.hillfort.helpers.readImageFromPath
import assignment.hillfort.models.HillfortModel
import kotlinx.android.synthetic.main.activity_hillfort.view.*
import kotlinx.android.synthetic.main.card_placemark.view.description
import kotlinx.android.synthetic.main.card_placemark.view.hillfortTitle
import kotlinx.android.synthetic.main.card_placemark.view.visited


interface HillfortListener {
    fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(private var hillforts: List<HillfortModel>, private val listener: HillfortListener) :
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
        val hillfort = hillforts[holder.adapterPosition]
        holder.bind(hillfort,listener)
    }

    override fun getItemCount(): Int = hillforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: HillfortListener) {


            if(hillfort.visited == true){
                itemView.visited.setChecked(true)
                hillfort.visited = true
            }else if(hillfort.visited == false){
                hillfort.visited = false
                itemView.visited.setChecked(false)
            }

            itemView.lat1.text = hillfort.location.lat.toString()
            itemView.lang.text = hillfort.location.lng.toString()
            itemView.hillfortTitle.text = hillfort.title
            itemView.description.text = hillfort.description
            itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }
}