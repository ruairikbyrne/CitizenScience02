package ie.wit.citizenscience.ui.sightings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
import timber.log.Timber.i
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingModel
import ie.wit.citizenscience.models.TaxaDesignationManager
import ie.wit.citizenscience.models.TaxaDesignationModel

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