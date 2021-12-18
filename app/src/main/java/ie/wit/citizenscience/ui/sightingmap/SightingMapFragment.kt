package ie.wit.citizenscience.ui.sightingmap


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
    private val sightingListViewModel: SightingListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    lateinit var loader : AlertDialog
    private lateinit var sightings: List<SightingModel>

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

        sightingListViewModel.observableSightingsList.observe(viewLifecycleOwner, Observer { sightings ->
            this.sightings = sightings
                updateMap()
                //hideLoader(loader)
                //checkSwipeRefresh()
            }

        )
            mapFragment.getMapAsync { googleMap ->
                mMap = googleMap
                mapReady = true
                updateMap()
            }
            //loader = createLoader(requireActivity())
            //activity?.title = getString(R.string.action_reported_sightings)


            return root
        }


    private fun updateMap() {
        if (mapReady && sightings != null) {
            sightings.forEach { sighting ->
                //if (!sighting.lat.isEmpty() && !sighting.lng.IsEmpty()) {
                    val loc = LatLng(sighting.lat, sighting.lng)
                    val options =
                        MarkerOptions().title(sighting.classification).snippet(sighting.species)
                            .position(loc)
                    //map.addMarker(options).tag = it.id
                    mMap.addMarker(options).tag = sighting.image
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, sighting.zoom))
                //}

            }
        }

    }

    override fun onResume() {
        super.onResume()
        showLoader(loader,"Downloading Sightings")
        loggedInViewModel.liveFirebaseUser.observe(viewLifecycleOwner, Observer { firebaseUser ->
            if (firebaseUser != null) {
                sightingListViewModel.liveFirebaseUser.value = firebaseUser
                sightingListViewModel.load()
            }
        })
    }
}