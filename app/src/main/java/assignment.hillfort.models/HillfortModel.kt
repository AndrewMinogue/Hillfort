package assignment.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HillfortModel(var id: Long = 0,
                         var image: String = "",
                         var image1: String = "",
                         var image2: String = "",
                         var image3: String = "",
                         var title: String = "",
                         var notes: String = "",
                         var description: String = "",
                         var lat : Double = 0.0,
                         var lng: Double = 0.0,
                         var visited: Boolean = false,
                         var datevisited: String = "",
                         var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

