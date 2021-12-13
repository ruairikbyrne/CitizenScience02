package ie.wit.citizenscience.models


import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*

const val JSON_FILE = "sightings.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<SightingModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class SightingJSONStore(private val context: Context) : SightingStore {

    var sightings = mutableListOf<SightingModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }
/*
    override fun findAll(): MutableList<SightingModel> {
        logAll()
        return sightings
    }
*/

    override fun findAll(sightingsList: MutableLiveData<List<SightingModel>>) {
        TODO("Not yet implemented")
    }

    override fun findAll(userid: String, sightingsList: MutableLiveData<List<SightingModel>>) {
        TODO("Not yet implemented")
    }

    override fun findById(userid: String, sightingid: String, sighting: MutableLiveData<SightingModel>) {
        TODO("Not yet implemented")
    }
/*
    override fun findById(id:Long) : SightingModel? {
        val foundSighting: SightingModel? = SightingManager.sightings.find { it.id == id }
        return foundSighting
    }
*/

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, sighting: SightingModel) {
        Timber.i("Firebase DB Reference : ${FirebaseDBManager.database}")

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
    }
    /*
    override fun create(sighting: SightingModel) {
        sighting.id = generateRandomId()
        sightings.add(sighting)
        serialize()
    }
*/

/*
    override fun delete(sighting: SightingModel) {
        var foundSighting: SightingModel? = sightings.find { s -> s.id == sighting.id }
        if (foundSighting != null) {
            sightings.remove(foundSighting)
            serialize()
            logAll()
        }
    }
*/

    override fun delete(userid: String, sightingid: String) {
        TODO("Not yet implemented")
    }

    /*
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
*/


    override fun update(userid: String, sightingid: String, sighting: SightingModel) {
        TODO("Not yet implemented")
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(sightings, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        sightings = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        sightings.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}


