package ie.wit.citizenscience.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.ActivitySightingBinding
import ie.wit.citizenscience.helpers.showImagePicker
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.Location
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber
import timber.log.Timber.i

class SightingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySightingBinding
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var sighting = SightingModel()
    lateinit var app : MainApp
    var location = Location(52.292, -6.497, 16f)

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
            Picasso.get()
                .load(sighting.image)
                .into(binding.sightingImage)
            if (sighting.image != Uri.EMPTY) {
                binding.chooseImage.setText((R.string.button_updateImage))
            }
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            i("Select image")
        }

        binding.sightingLocation.setOnClickListener {
            i ("Set Location Pressed")
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
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
        registerImagePickerCallback()
        registerMapCallback()
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
                                .into(binding.sightingImage)
                            binding.chooseImage.setText((R.string.button_updateImage))
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            location = result.data!!.extras?.getParcelable("location")!!
                            i("Location == $location")
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}