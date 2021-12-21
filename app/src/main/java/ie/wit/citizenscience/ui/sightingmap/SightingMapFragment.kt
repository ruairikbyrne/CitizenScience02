package ie.wit.citizenscience.ui.sightingmap


import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.FragmentSightingListBinding
import ie.wit.citizenscience.databinding.FragmentSightingMapBinding
import ie.wit.citizenscience.helpers.createLoader
import ie.wit.citizenscience.helpers.hideLoader
import ie.wit.citizenscience.helpers.showLoader
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingManager.sightings
import ie.wit.citizenscience.models.SightingModel
import ie.wit.citizenscience.ui.auth.LoggedInViewModel
import ie.wit.citizenscience.ui.sightingslist.SightingListViewModel

class SightingMapFragment : Fragment() {

    lateinit var app: MainApp
    private var _fragBinding: FragmentSightingMapBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var mMap: GoogleMap
    private var mapReady = false
    private val sightingMapViewModel: SightingMapViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private lateinit var loader : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as MainApp
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _fragBinding = FragmentSightingMapBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment

        activity?.title = getString(R.string.action_reported_sightings_map)
        sightingMapViewModel.observableSightingsMapList.observe(viewLifecycleOwner, Observer { sightings ->
            sightings?.let {
                updateMap(sightings as ArrayList<SightingModel>)
                hideLoader(loader)
            }

        })

            mapFragment.getMapAsync { googleMap ->
                mMap = googleMap
                mapReady = true
                //updateMap(sightings as ArrayList<SightingModel>)
            }
            loader = createLoader(requireActivity())



            return root
        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sightinglist, menu)

        val item = menu.findItem(R.id.toggleSightings) as MenuItem
        item.setActionView(R.layout.togglebutton_layout)
        val toggleSightings: SwitchCompat = item.actionView.findViewById(R.id.toggleButton)
        toggleSightings.isChecked = false

        toggleSightings.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) sightingMapViewModel.loadAll()
            else sightingMapViewModel.load()

        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }

    private fun updateMap(sightings: ArrayList<SightingModel>) {
        mMap.clear()
        if (mapReady && sightings != null) {
            sightings.forEach { sighting ->
                    val loc = LatLng(sighting.lat, sighting.lng)
                    val options =
                        MarkerOptions().title(sighting.classification).snippet(sighting.species)
                            .position(loc)
                    //map.addMarker(options).tag = it.id
                    mMap.addMarker(options).tag = sighting.image
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, sighting.zoom))

            }
        }

    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Sightings")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                sightingMapViewModel.liveFirebaseUser.value = firebaseUser
                sightingMapViewModel.load()
            }
        })
    }
}