package ie.wit.citizenscience.firebase

import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ie.wit.citizenscience.models.SightingModel
import ie.wit.citizenscience.models.SightingStore


object FirebaseDBManager : SightingStore {

    var database: DatabaseReference = FirebaseDatabase.getInstance().reference

    override fun findAll(sightingsList: MutableLiveData<List<SightingModel>>) {
        database.child("sightings")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Sighting error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<SightingModel>()
                    val children = snapshot.children
                    children.forEach {
                        val donation = it.getValue(SightingModel::class.java)
                        localList.add(donation!!)
                    }
                    database.child("sightings")
                        .removeEventListener(this)

                    sightingsList.value = localList
                }
            })
    }

    override fun findAll(userid: String, sightingsList: MutableLiveData<List<SightingModel>>) {

        Timber.i("User id passed into findall $userid")
        database.child("user-sightings").child(userid)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Timber.i("Firebase Sighting error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    val localList = ArrayList<SightingModel>()
                    val children = snapshot.children
                    children.forEach {
                        val sighting = it.getValue(SightingModel::class.java)
                        localList.add(sighting!!)
                    }
                    database.child("user-sightings").child(userid)
                        .removeEventListener(this)

                    sightingsList.value = localList
                }
            })
    }

    override fun findById(userid: String, sightingid: String, sighting: MutableLiveData<SightingModel>) {

        database.child("user-sightings").child(userid)
            .child(sightingid).get().addOnSuccessListener {
                sighting.value = it.getValue(SightingModel::class.java)
                Timber.i("firebase Got value ${it.value}")
            }.addOnFailureListener{
                Timber.e("firebase Error getting data $it")
            }
    }

    override fun create(firebaseUser: MutableLiveData<FirebaseUser>, sighting: SightingModel) {
        Timber.i("Firebase DB Reference : $database")

        val uid = firebaseUser.value!!.uid
        val key = database.child("sightings").push().key
        if (key == null) {
            Timber.i("Firebase Error : Key Empty")
            return
        }
        sighting.uid = key
        val sightingValues = sighting.toMap()

        val childAdd = HashMap<String, Any>()
        childAdd["/sightings/$key"] = sightingValues
        childAdd["/user-sightings/$uid/$key"] = sightingValues

        database.updateChildren(childAdd)

    }

    override fun delete(userid: String, sightingid: String) {
        Timber.i("Firebase DB Manager : Delete Function")
        val childDelete : MutableMap<String, Any?> = HashMap()
        childDelete["/sightings/$sightingid"] = null
        childDelete["/user-sightings/$userid/$sightingid"] = null

        database.updateChildren(childDelete)
    }

    override fun update(userid: String, sightingid: String, sighting: SightingModel) {
        val sightingValues = sighting.toMap()

        val childUpdate : MutableMap<String, Any?> = HashMap()
        childUpdate["sightings/$sightingid"] = sightingValues
        childUpdate["user-sightings/$userid/$sightingid"] = sightingValues

        database.updateChildren(childUpdate)
    }
}