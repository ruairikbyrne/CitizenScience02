package ie.wit.citizenscience.api

import ie.wit.citizenscience.models.TaxaDesignationModel
import ie.wit.citizenscience.models.TaxaSpeciesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TaxaDesignationService {
    @GET("/api/taxonGroup/lookupInput?search=")
    fun getall(): Call<ArrayList<TaxaDesignationModel>>

    @GET("/api/taxon/summary")
    fun getSpecies(@Query("taxonGroupName") classification:String): Call<ArrayList<TaxaSpeciesModel>>
}