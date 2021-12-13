package ie.wit.citizenscience.models

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class SightingModel(var uid : String? = "",
                         var classification: String = "Test",
                         var species: String = "Test",
                         var image: Uri = Uri.EMPTY,
                         var lat: Double = 0.0,
                         var lng: Double = 0.0,
                         var zoom: Float = 0f,
                         var email: String = "joe@bloggs.com") : Parcelable

{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "classification" to classification,
            "species" to species,
            //"image" to image,
            "lat" to lat,
            "lng" to lng,
            "zoom" to zoom,
            "email" to email
        )
    }
}

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable

