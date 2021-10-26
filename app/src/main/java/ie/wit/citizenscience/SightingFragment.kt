package ie.wit.citizenscience

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.activities.Home
import ie.wit.citizenscience.activities.MapActivity
import ie.wit.citizenscience.databinding.FragmentSightingBinding
import ie.wit.citizenscience.databinding.FragmentSightingListBinding
import ie.wit.citizenscience.helpers.showImagePicker
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.Location
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber

class SightingFragment : Fragment() {

    lateinit var app: MainApp

    private var _fragBinding: FragmentSightingBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var sighting = SightingModel()
    var edit = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as MainApp


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentSightingBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_add_sighting)


        //val intent = Intent (getActivity(), Home::class.java)
        val intent = Intent (app.applicationContext, Home::class.java)
        startActivity(intent)

        if (intent.hasExtra("sighting_edit")) {
            edit = true
            sighting = intent.extras?.getParcelable("sighting_edit")!!
            fragBinding.sightingClassification.setText(sighting.classification)
            fragBinding.sightingSpecies.setText(sighting.species)
            fragBinding.btnAdd.setText(R.string.button_updateSighting)
            Picasso.get()
                .load(sighting.image)
                .into(fragBinding.sightingImage)
            if (sighting.image != Uri.EMPTY) {
                fragBinding.chooseImage.setText((R.string.button_updateImage))
            }
        }

        setButtonListener(fragBinding)
        registerImagePickerCallback()
        registerMapCallback()
        return root;
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onResume() {
        super.onResume()

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                activity?.finish()
            }
            R.id.item_delete -> {
                app.sightings.delete(sighting.copy())
                activity?.finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun setButtonListener(layout: FragmentSightingBinding) {
        layout.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
            Timber.i("Select image")
        }

        layout.sightingLocation.setOnClickListener {
            Timber.i("Set Location Pressed")
            val location = Location(52.292, -6.497, 16f)
            if (sighting.zoom != 0f) {
                location.lat =  sighting.lat
                location.lng = sighting.lng
                location.zoom = sighting.zoom
            }
            val launcherIntent = Intent(getActivity(), MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        layout.btnAdd.setOnClickListener() {
            sighting.classification = fragBinding.sightingClassification.text.toString()
            sighting.species = fragBinding.sightingSpecies.text.toString()
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
            activity?.setResult(AppCompatActivity.RESULT_OK)
            activity?.finish()

        }
        /*
        layout.donateButton.setOnClickListener {
            val amount = if (layout.paymentAmount.text.isNotEmpty())
                layout.paymentAmount.text.toString().toInt() else layout.amountPicker.value
            if(totalDonated >= layout.progressBar.max)
                Toast.makeText(context,"Donate Amount Exceeded!",Toast.LENGTH_LONG).show()
            else {
                val paymentmethod = if(layout.paymentMethod.checkedRadioButtonId == R.id.Direct) "Direct" else "Paypal"
                totalDonated += amount
                layout.totalSoFar.text = "$$totalDonated"
                layout.progressBar.progress = totalDonated
                app.donationsStore.create(DonationModel(paymentmethod = paymentmethod,amount = amount))
            }

         */

    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            sighting.image = result.data!!.data!!
                            Picasso.get()
                                .load(sighting.image)
                                .into(fragBinding.sightingImage)
                            fragBinding.chooseImage.setText((R.string.button_updateImage))
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    AppCompatActivity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            Timber.i("Location == $location")
                            sighting.lat = location.lat
                            sighting.lng = location.lng
                            sighting.zoom = location.zoom
                        } // end of if
                    }
                    AppCompatActivity.RESULT_CANCELED -> { } else -> { }
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


}
