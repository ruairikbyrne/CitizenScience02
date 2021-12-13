package ie.wit.citizenscience.ui.sightingslist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber
import java.lang.Exception

class SightingListViewModel : ViewModel() {


    private val sightingsList = MutableLiveData<List<SightingModel>>()


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
            FirebaseDBManager.findAll(liveFirebaseUser.value?.uid!!, sightingsList)
            Timber.i("Report Load Success : ${sightingsList.value.toString()}")
        }
        catch (e: Exception) {
            Timber.i("List Sighting Report Load Error : $e.message")
        }

    }

    fun delete(userid: String, id: String) {
        try {
            Timber.i("Hitting delete call")
            FirebaseDBManager.delete(userid,id)
            Timber.i("Report Delete Success")
        }
        catch (e: Exception) {
            Timber.i("Report Delete Error : $e.message")
        }
    }
}