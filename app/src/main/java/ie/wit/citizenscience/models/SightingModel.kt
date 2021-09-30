package ie.wit.citizenscience.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SightingModel(var id : Long = 0,
                            var classification: String = "",
                            var species: String = "") : Parcelable
