package ie.wit.citizenscience.ui.sightingmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.models.SightingModel

import timber.log.Timber

class SightingMapViewModel {

    private val sightingsList = MutableLiveData<List<SightingModel>>()
    var readOnly = MutableLiveData(false)


    val observableSightingsList: LiveData<List<SightingModel>>
        get() = sightingsList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {

        load()
    }

    fun load() {

        try {
            Timber.i("Trying to call firebase findall, userid : ${liveFirebaseUser.value?.uid!!}")
            //sightingsList.value = SightingManager.findAll()
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, sightingsList)
            Timber.i("Report Load Success : ${sightingsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List Sighting Report Load Error : $e.message")
        }

    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(sightingsList)
            Timber.i("Report LoadAll Success : ${sightingsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Report LoadAll Error : $e.message")
        }
    }

}