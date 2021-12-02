package ie.wit.citizenscience.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingModel

class SightingDetailViewModel : ViewModel() {
    private val sighting = MutableLiveData<SightingModel>()
    var observableSighting: LiveData<SightingModel>
        get() = sighting
        set(value) {sighting.value = value.value}

    fun getSighting(id: Long) {
        sighting.value = SightingManager.findById(id)
    }
}