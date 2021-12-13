package ie.wit.citizenscience.models

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import ie.wit.citizenscience.firebase.FirebaseDBManager
import timber.log.Timber
import timber.log.Timber.i
/*
var lastId = 0L

internal fun getId(): Long {
    return lastId++
}
*/
object SightingManager : SightingStore {
    val sightings = ArrayList<SightingModel>()

    override fun findAll(sightingsList: MutableLiveData<List<SightingModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, sightingsList: MutableLiveData<List<SightingModel>>) {
        TODO("Not yet implemented")
    }

    override fun findById(userid: String, sightingid: String, sighting: MutableLiveData<SightingModel>) {
        TODO("Not yet implemented")
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, sighting: SightingModel) {
        Timber.i("Firebase DB Reference : ${FirebaseDBManager.database}")
        /*
        val uid = firebaseUser.value!!.uid
        val key = FirebaseDBManager.database.child("sightings").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        sighting.uid = key
        val sightingValues = sighting.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/sightings/$key"] = sightingValues
        childAdd["/user-sightings/$uid/$key"] = sightingValues

        FirebaseDBManager.database.updateChildren(childAdd)

         */
    }

    override fun delete(userid: String, sightingid: String) {
        TODO("Not yet implemented")
    }

    override fun update(userid: String, sightingid: String, sighting: SightingModel) {
        TODO("Not yet implemented")
    }
/*
    override fun findAll(): List<SightingModel> {
        return sightings
    }

    override fun findById(id:Long) : SightingModel? {
        val foundSighting: SightingModel? = sightings.find { it.id == id }
        i("Found Record: $foundSighting")
        return foundSighting
    }
    override fun create(sighting: SightingModel) {
        sighting.id = getId()
        sightings.add(sighting)
        logAll()
    }

    override fun delete(sighting: SightingModel) {
        var foundSighting: SightingModel? = sightings.find { s -> s.id == sighting.id }
        if (foundSighting != null) {
            sightings.remove(foundSighting)
            logAll()
        }
    }
    override fun update(sighting: SightingModel) {
        var foundSighting: SightingModel? = sightings.find { s -> s.id == sighting.id }
        if (foundSighting != null) {
            foundSighting.classification = sighting.classification
            foundSighting.species = sighting.species
            foundSighting.image = sighting.image
            foundSighting.lat = sighting.lat
            foundSighting.lng = sighting.lng
            foundSighting.zoom = sighting.zoom
            logAll()
        }
    }

    fun logAll() {
        sightings.forEach{ i("${it}") }
    }
*/
}