package ie.wit.citizenscience.models

import androidx.lifecycle.MutableLiveData
import ie.wit.citizenscience.api.TaxaDesignationClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


object TaxaDesignationManager : TaxaDesignationStore {
    //val taxaDesignations = ArrayList<TaxaDesignationModel>()

    override fun findAll(taxaDesignationList: MutableLiveData<ArrayList<TaxaDesignationModel>>) {

        val call = TaxaDesignationClient.getApi().getall()


        call.enqueue(object : Callback<ArrayList<TaxaDesignationModel>> {
            override fun onResponse(call: Call<ArrayList<TaxaDesignationModel>>,
                                    response: Response<ArrayList<TaxaDesignationModel>>
            ) {

                taxaDesignationList.value = response.body() as ArrayList<TaxaDesignationModel>

                Timber.i("Retrofit JSON = ${response.body()}")
                val returnedValue = taxaDesignationList.value?.map { it.name }
                Timber.i("Manager Item list: $returnedValue")
            }

            override fun onFailure(call: Call<ArrayList<TaxaDesignationModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }
}