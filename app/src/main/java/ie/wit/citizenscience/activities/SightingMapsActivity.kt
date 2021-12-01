package ie.wit.citizenscience.activities

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.databinding.ActivitySightingMapsBinding
import ie.wit.citizenscience.databinding.ContentSightingMapsBinding
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingModel
/*
class SightingMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: ActivitySightingMapsBinding
    private lateinit var contentBinding: ContentSightingMapsBinding
    lateinit var map: GoogleMap
    lateinit var app: MainApp
    lateinit var imageid : Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp
        binding = ActivitySightingMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
        contentBinding = ContentSightingMapsBinding.bind(binding.root)
        contentBinding.mapView.onCreate(savedInstanceState)
        contentBinding.mapView.getMapAsync {
            map = it
            configureMap()
        }
    }

    fun configureMap() {
        map.setOnMarkerClickListener(this)
        map.uiSettings.setZoomControlsEnabled(true)
        app.sightings.findAll().forEach {
            val loc = LatLng(it.lat, it.lng)


            val options = MarkerOptions().title(it.classification).snippet(it.species).position(loc)
            //map.addMarker(options).tag = it.id
            map.addMarker(options).tag = it.image
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        imageid = marker.tag as Uri
        contentBinding.currentTitle.text = marker.title
        contentBinding.currentDescription.text = marker.snippet
        //contentBinding.currentDescription.text = marker.tag.toString()
        Picasso.get().load(imageid).resize(200,200).into(contentBinding.currentImage)


        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        contentBinding.mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        contentBinding.mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        contentBinding.mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        contentBinding.mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        contentBinding.mapView.onSaveInstanceState(outState)
    }


}

 */