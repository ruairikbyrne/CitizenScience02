package ie.wit.citizenscience.main

import android.app.Application
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    var sightings = ArrayList<SightingModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Citizen Science started")
        sightings.add(SightingModel("Bird", "Swallow"))
        sightings.add(SightingModel("Bird", "Finch"))
        sightings.add(SightingModel("Butterfly", "Red Admiral"))
    }
}