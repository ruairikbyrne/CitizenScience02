package ie.wit.citizenscience.ui.sightingmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.models.SightingModel

import timber.log.Timber

class SightingMapViewModel : ViewModel() {

    private val sightingsMapList = MutableLiveData<List<SightingModel>>()
    var readOnly = MutableLiveData(false)


    val observableSightingsMapList: LiveData<List<SightingModel>>
        get() = sightingsMapList

    var liveFirebaseUser = MutableLiveData<FirebaseUser>()

    init {

        load()
    }

    fun load() {

        try {
            Timber.i("Trying to call firebase findall, userid : ${liveFirebaseUser.value?.uid!!}")
            //sightingsList.value = SightingManager.findAll()
            readOnly.value = false
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, sightingsMapList)
            Timber.i("Map User Sightings Load Success : ${sightingsMapList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Map User Sightings Load Error : $e.message")
        }

    }

    fun loadAll() {
        try {
            readOnly.value = true
            FirebaseDBManager.findAll(sightingsMapList)
            Timber.i("Map Load All Sightings Success : ${sightingsMapList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("Map Load All Sightings Error : $e.message")
        }
    }

}