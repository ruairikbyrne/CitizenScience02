package ie.wit.citizenscience.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser

interface SightingStore {
    fun findAll(sightingsList:
                MutableLiveData<List<SightingModel>>)
    fun findAll(userid:String,
                sightingsList:
                MutableLiveData<List<SightingModel>>)
    fun findById(userid : String, sightingid: String, sighting : MutableLiveData<SightingModel>)
    fun create(firebaseUser : MutableLiveData<FirebaseUser>, sighting : SightingModel)
    fun update(userid : String, sightingid : String, sighting : SightingModel)
    //fun delete(sighting: SightingModel)
    fun delete(userid : String, sightingid: String)

}