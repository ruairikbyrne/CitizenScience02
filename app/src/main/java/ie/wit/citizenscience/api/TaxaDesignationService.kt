package ie.wit.citizenscience.api

import ie.wit.citizenscience.models.TaxaDesignationModel
import retrofit2.Call
import retrofit2.http.GET

interface TaxaDesignationService {
    @GET("/api/taxonGroup/lookupInput?search=")
    fun getall(): Call<ArrayList<TaxaDesignationModel>>
}