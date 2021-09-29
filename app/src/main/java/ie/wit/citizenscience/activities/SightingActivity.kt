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

        binding = ActivitySightingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp
        i("Sighting Activity started...")

        binding.btnAdd.setOnClickListener() {
            sighting.classification = binding.sightingClassification.text.toString()
            sighting.species = binding.sightingSpecies.text.toString()
            if (sighting.classification.isNotEmpty()) {
                app.sightings.create(sighting.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"Please Enter a title", Snackbar.LENGTH_LONG)
                    .show()
            }
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