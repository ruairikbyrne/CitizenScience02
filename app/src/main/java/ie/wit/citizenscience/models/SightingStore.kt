package ie.wit.citizenscience.models

interface SightingStore {
    fun findAll(): List<SightingModel>
    fun create(sighting: SightingModel)
    fun update(sighting: SightingModel)
    fun delete(sighting: SightingModel)
}