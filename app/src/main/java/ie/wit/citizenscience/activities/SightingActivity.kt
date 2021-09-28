package ie.wit.citizenscience.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.citizenscience.databinding.ActivitySightingBinding
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber
import timber.log.Timber.i

class SightingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySightingBinding
    var sighting = SightingModel()
    lateinit var app : MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySightingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        i("Sighting Activity started...")

        binding.btnAdd.setOnClickListener() {
            sighting.classification = binding.sightingClassification.text.toString()
            sighting.species = binding.sightingSpecies.text.toString()
            if (sighting.classification.isNotEmpty()) {
                app.sightings.add(sighting.copy())
                i("add Button Pressed: Classification: ${sighting.classification} Species: ${sighting.species}")
                for (i in app.sightings.indices)
                    { i("Sighting[$i]:${this.app.sightings[i]}")}
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }
}