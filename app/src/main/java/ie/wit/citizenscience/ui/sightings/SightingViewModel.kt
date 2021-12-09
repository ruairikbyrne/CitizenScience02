package ie.wit.citizenscience.ui.sightings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ie.wit.citizenscience.models.*
import timber.log.Timber
import timber.log.Timber.i

class SightingViewModel : ViewModel() {

    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status




    fun addSighting(sighting: SightingModel) {
        status.value = try {
            SightingManager.create(sighting)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }




}