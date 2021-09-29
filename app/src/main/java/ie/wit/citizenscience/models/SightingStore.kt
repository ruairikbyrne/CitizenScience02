package ie.wit.citizenscience.models

interface SightingStore {
    fun findAll(): List<SightingModel>
    fun create(sighting: SightingModel)
}