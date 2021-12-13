package ie.wit.citizenscience.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber

class SightingDetailViewModel : ViewModel() {
    private val sighting = MutableLiveData<SightingModel>()
    var observableSighting: LiveData<SightingModel>
        get() = sighting
        set(value) {sighting.value = value.value}

    fun getSighting(userid:String, id: String) {
        try {
            //sighting.value = SightingManager.findById(uid)
            FirebaseDBManager.findById(userid, id, sighting)
            Timber.i("Detail getSighting() Success : ${sighting.value.toString()}")

        }
        catch (e: Exception) {
            Timber.i("Detail getSighting() Error : $e.message")
        }
    }

    fun updateSighting(userid:String, id: String, sighting: SightingModel) {
        try {
            FirebaseDBManager.update(userid, id, sighting)
            Timber.i("Record id : $id")
            Timber.i("Detail update() Success : $sighting")
        }
        catch (e: Exception) {
            Timber.i("Detail update() Error : $e.message")
        }
    }
}