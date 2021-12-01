package ie.wit.citizenscience.ui.sightingslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingModel

class SightingListViewModel : ViewModel() {

    private val sightingsList = MutableLiveData<List<SightingModel>>()

    val observableSightingsList: LiveData<List<SightingModel>>
        get() = sightingsList

    init {
        load()
    }

    fun load() {
        sightingsList.value = SightingManager.findAll()
    }
}