package ie.wit.citizenscience.ui.sightings

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.FragmentSightingBinding
import ie.wit.citizenscience.helpers.showImagePicker
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingModel
import ie.wit.citizenscience.models.TaxaDesignationManager
import ie.wit.citizenscience.models.TaxaDesignationModel
import timber.log.Timber
import timber.log.Timber.i


class SightingFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentSightingBinding? = null
    private val fragBinding get() = _fragBinding!!
    var sighting = SightingModel()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var sightingViewModel: SightingViewModel

    private val taxaDesignationList =
        MutableLiveData<List<TaxaDesignationModel>>()

    val observableTaxaDesignationList: LiveData<List<TaxaDesignationModel>>
        get() = taxaDesignationList


    init { load() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        app = activity?.application as MainApp

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentSightingBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        sightingViewModel = ViewModelProvider(this).get(SightingViewModel::class.java)
        sightingViewModel.observableStatus.observe(viewLifecycleOwner, Observer {
                status -> status?.let { render(status) }
        })
        //val adapter = ListAdapter(this, android.R.layout.simple_spinner_item, observableTaxaDesignationList )
        //fragBinding.spinner.adapter = adapter


        activity?.title = getString(R.string.action_add_sighting)

        setButtonListener(fragBinding)
        registerImagePickerCallback()
        return root
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    //Uncomment this if you want to immediately return to Report
                    findNavController().popBackStack()
                }
            }
            false -> Toast.makeText(context,getString(R.string.sightingError),Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sighting, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()

    }

    fun setButtonListener(layout: FragmentSightingBinding) {
        layout.btnAdd.setOnClickListener {
            //sighting.classification = layout.sightingClassification.text.toString()
            //sighting.species = layout.sightingSpecies.text.toString()
            val classification = layout.sightingClassification.text.toString()
            val species = layout.sightingSpecies.text.toString()


            if (classification.isEmpty()) {
                Snackbar.make(it, "Please enter a classification", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                    //app.sightings.create(sighting.copy())
                    sightingViewModel.addSighting(SightingModel(classification = classification, species = species, image = sighting.image))
            }
        }

        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            Timber.i("Select image")
        }

    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            sighting.image = result.data!!.data!!

                            Picasso.get()
                                .load(sighting.image)
                                .into(_fragBinding?.sightingImage)
                            //binding.chooseImage.setText((R.string.button_updateImage))
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            SightingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    fun load() {
        try {
            TaxaDesignationManager.findAll(taxaDesignationList)
            i("Retrofit Success : $taxaDesignationList.value")

        } catch (e: Exception) {
            i("Retrofit Error : $e.message")
        }
    }
}