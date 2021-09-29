package ie.wit.citizenscience.models

import timber.log.Timber.i

class SightingMemStore : SightingStore {
    val sightings = ArrayList<SightingModel>()

    override fun findAll(): List<SightingModel> {
        return sightings
    }

    override fun create(sighting: SightingModel) {
        sightings.add(sighting)
        logAll()
    }

    fun logAll() {
        sightings.forEach{ i("${it}") }
    }
}