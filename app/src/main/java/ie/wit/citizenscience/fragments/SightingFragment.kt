package ie.wit.citizenscience.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.FragmentSightingBinding
import ie.wit.citizenscience.helpers.showImagePicker
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber



class SightingFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentSightingBinding? = null
    private val fragBinding get() = _fragBinding!!
    var sighting = SightingModel()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>

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
        activity?.title = getString(R.string.action_add_sighting)

        setButtonListener(fragBinding)
        return root
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
            sighting.classification = layout.sightingClassification.text.toString()
            sighting.species = layout.sightingSpecies.text.toString()
            if (sighting.classification.isEmpty()) {
                Snackbar.make(it, "Please enter a classification", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                    app.sightings.create(sighting.copy())
            }
        }
        /*
        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            Timber.i("Select image")
        }
         */
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SightingFragment().apply {
                arguments = Bundle().apply {}
            }
    }

}