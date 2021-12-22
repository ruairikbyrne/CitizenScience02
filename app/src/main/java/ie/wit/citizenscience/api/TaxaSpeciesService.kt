package ie.wit.citizenscience.api


import ie.wit.citizenscience.models.TaxaDesignationModel
import ie.wit.citizenscience.models.TaxaSpeciesListModel
import ie.wit.citizenscience.models.TaxaSpeciesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TaxaSpeciesService {


        @GET("/api/taxon/summary")
        //fun getSpecies(@Query("taxonGroupName") classification:String): Call<ArrayList<TaxaSpeciesModel>>
        fun getSpecies(@Query("taxonGroupName") classification:String): Call<TaxaSpeciesListModel>


}