package ie.wit.citizenscience.models

import androidx.lifecycle.MutableLiveData

interface TaxaSpeciesStore {
    fun findSpecies(taxaSpeciesList: MutableLiveData<ArrayList<TaxaSpeciesModel>>, taxaClassification: String)
}