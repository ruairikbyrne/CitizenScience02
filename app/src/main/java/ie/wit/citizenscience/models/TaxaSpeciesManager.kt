package ie.wit.citizenscience.models

import androidx.lifecycle.MutableLiveData
import ie.wit.citizenscience.api.TaxaSpeciesClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

object TaxaSpeciesManager : TaxaSpeciesStore {
    override fun findSpecies(taxaSpeciesList: MutableLiveData<ArrayList<TaxaSpeciesModel>>, taxaClassification: String) {


        //val call = TaxaSpeciesClient.getApi().getSpecies("Birds")
        val call = TaxaSpeciesClient.getApi().getSpecies(taxaClassification)



        call.enqueue(object : Callback<TaxaSpeciesListModel> {
            override fun onResponse(call: Call<TaxaSpeciesListModel>,
                                    response: Response<TaxaSpeciesListModel>
            ) {

                val body = response?.body()
                taxaSpeciesList.value = body?.data as ArrayList<TaxaSpeciesModel>

                Timber.i("Retrofit JSON = ${response.body()}")
                val returnedValue = taxaSpeciesList.value?.map { it.commonName }
                Timber.i("Manager Item list: $returnedValue")
            }

            override fun onFailure(call: Call<TaxaSpeciesListModel>, t: Throwable) {
                Timber.i("Retrofit Species Error : $t.message")
            }
        })

/*
        call.enqueue(object : Callback<ArrayList<TaxaSpeciesModel>> {
            override fun onResponse(call: Call<ArrayList<TaxaSpeciesModel>>,
                                    response: Response<ArrayList<TaxaSpeciesModel>>
            ) {

                taxaSpeciesList.value = response.body() as ArrayList<TaxaSpeciesModel>

                Timber.i("Retrofit JSON = ${response.body()}")
                val returnedValue = taxaSpeciesList.value?.map { it.commonName }
                Timber.i("Manager Item list: $returnedValue")
            }

            override fun onFailure(call: Call<ArrayList<TaxaSpeciesModel>>, t: Throwable) {
                Timber.i("Retrofit Species Error : $t.message")
            }
        })
 */


    }
}