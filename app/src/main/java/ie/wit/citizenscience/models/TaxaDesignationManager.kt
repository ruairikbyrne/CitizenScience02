package ie.wit.citizenscience.models

import androidx.lifecycle.MutableLiveData
import ie.wit.citizenscience.api.TaxaDesignationClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber


object TaxaDesignationManager : TaxaDesignationStore {
    val taxaDesignations = ArrayList<TaxaDesignationModel>()

    override fun findAll(taxaDesignationList: MutableLiveData<List<TaxaDesignationModel>>) {

        val call = TaxaDesignationClient.getApi().getall()


        call.enqueue(object : Callback<List<TaxaDesignationModel>> {
            override fun onResponse(call: Call<List<TaxaDesignationModel>>,
                                    response: Response<List<TaxaDesignationModel>>
            ) {
                taxaDesignationList.value = response.body() as ArrayList<TaxaDesignationModel>
                Timber.i("Retrofit JSON = ${response.body()}")
            }

            override fun onFailure(call: Call<List<TaxaDesignationModel>>, t: Throwable) {
                Timber.i("Retrofit Error : $t.message")
            }
        })
    }
}