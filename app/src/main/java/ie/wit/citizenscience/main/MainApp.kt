package ie.wit.citizenscience.main

import android.app.Application
import ie.wit.citizenscience.models.SightingJSONStore
import ie.wit.citizenscience.models.SightingManager
import ie.wit.citizenscience.models.SightingStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    //val sightings = SightingMemStore()
    //lateinit var sightings: SightingStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Citizen Science started")
        //sightings = SightingJSONStore(applicationContext)

    }
}