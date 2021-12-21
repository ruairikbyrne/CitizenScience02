package ie.wit.citizenscience.models

import android.net.Uri
import androidx.lifecycle.MutableLiveData


interface TaxaDesignationStore {
    fun findAll(taxaDesignationList: MutableLiveData<ArrayList<TaxaDesignationModel>>)
}