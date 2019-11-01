package assignment.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var username: String = "",
                     var password: String = "") : Parcelable
