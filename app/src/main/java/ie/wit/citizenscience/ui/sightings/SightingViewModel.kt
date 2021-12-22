package ie.wit.citizenscience.ui.sightings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import ie.wit.citizenscience.firebase.FirebaseDBManager
import ie.wit.citizenscience.firebase.FirebaseImageManager
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.*
import timber.log.Timber
import timber.log.Timber.i

class SightingViewModel : ViewModel() {

    //lateinit var app : MainApp
    //var sighting = SightingModel()

    private val status = MutableLiveData<Boolean>()
        val observableStatus: LiveData<Boolean>
        get() = status

    private val taxaDesignationList = MutableLiveData<ArrayList<TaxaDesignationModel>>()
    val observableTaxaDesignationList: LiveData<ArrayList<TaxaDesignationModel>>
        get() = taxaDesignationList

    private val taxaSpeciesList = MutableLiveData<ArrayList<TaxaSpeciesModel>>()
    val observableTaxaSpeciesList: LiveData<ArrayList<TaxaSpeciesModel>>
        get() = taxaSpeciesList

    init {
        loadClassification()
    }

    fun addSighting(firebaseUser: MutableLiveData<FirebaseUser>, sighting: SightingModel) {
        status.value = try {

            //SightingManager.create(sighting)

            sighting.image = FirebaseImageManager.imageUri.value.toString()
            i("Hit add sighting")
            FirebaseDBManager.create(firebaseUser, sighting)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }



    fun loadClassification() {
        try {
            TaxaDesignationManager.findAll(taxaDesignationList)
            i("Retrofit Taxon Group Success : $taxaDesignationList")

            //TaxaSpeciesManager.findSpecies(taxaSpeciesList, "Birds")
            //i("Retrofit Species Success : $taxaSpeciesList")

        } catch (e: Exception) {
            i("Retrofit Error SightingVM: $e.message")
        }
    }

    fun loadSpecies(selectedValue:String) {
        try {

            //TaxaSpeciesManager.findSpecies(taxaSpeciesList, "Birds")
            TaxaSpeciesManager.findSpecies(taxaSpeciesList, selectedValue)
            i("Retrofit Species Success : $taxaSpeciesList")

        } catch (e: Exception) {
            i("Retrofit Error SightingVM: $e.message")
        }
    }


}