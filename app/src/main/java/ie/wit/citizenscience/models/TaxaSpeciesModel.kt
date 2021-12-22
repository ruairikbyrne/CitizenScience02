package ie.wit.citizenscience.models

data class TaxaSpeciesModel(
    val commonName: String,
    val dataCount: Int,
    val gbifId: Int,
    val groupName: String,
    val hasProfile: Boolean,
    val prefnameTaxonId: Int,
    val taxonId: Int,
    val taxonName: String
)

{
    override fun toString() : String{
        return commonName
    }
}