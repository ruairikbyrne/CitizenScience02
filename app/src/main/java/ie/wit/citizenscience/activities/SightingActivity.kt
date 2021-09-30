package ie.wit.citizenscience.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.citizenscience.R
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
        var edit = false

        binding = ActivitySightingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Sighting Activity started...")

        if (intent.hasExtra("sighting_edit")) {
            edit = true
            sighting = intent.extras?.getParcelable("sighting_edit")!!
            binding.sightingClassification.setText(sighting.classification)
            binding.sightingSpecies.setText(sighting.species)
            binding.btnAdd.setText(R.string.button_updateSighting)
        }

        binding.btnAdd.setOnClickListener() {
            sighting.classification = binding.sightingClassification.text.toString()
            sighting.species = binding.sightingSpecies.text.toString()
            if (sighting.classification.isEmpty()) {
                Snackbar.make(it, "Please enter a classification", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.sightings.update(sighting.copy())
                } else {
                    app.sightings.create(sighting.copy())
                }


            }
            setResult(RESULT_OK)
            finish()

        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_sighting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}