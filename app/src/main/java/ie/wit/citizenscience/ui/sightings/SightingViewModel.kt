package ie.wit.citizenscience.ui.sightings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.*
import timber.log.Timber
import timber.log.Timber.i

class SightingViewModel : ViewModel() {

    //lateinit var app : MainApp
    //var sighting = SightingModel()

    private val status = MutableLiveData<Boolean>()
        val observableStatus: LiveData<Boolean>
        get() = status




    fun addSighting(firebaseUser: MutableLiveData<FirebaseUser>, sighting: SightingModel) {
        status.value = try {

            //SightingManager.create(sighting)
            FirebaseDBManager.create(firebaseUser, sighting)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }




}