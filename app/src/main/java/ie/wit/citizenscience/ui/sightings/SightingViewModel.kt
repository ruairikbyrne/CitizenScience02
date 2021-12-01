package ie.wit.citizenscience.ui.sightings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingModel

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