package ie.wit.citizenscience.models

data class TaxaDesignationModel(
    val name: String,
    val taxonGroupId: Int

)

{
    override fun toString() : String{
        return name
    }
}