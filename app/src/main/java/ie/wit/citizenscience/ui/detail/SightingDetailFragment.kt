package ie.wit.citizenscience.ui.detail

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.FragmentSightingBinding
import ie.wit.citizenscience.databinding.FragmentSightingDetailBinding
import ie.wit.citizenscience.firebase.FirebaseImageManager
import ie.wit.citizenscience.helpers.readImageUri
import ie.wit.citizenscience.helpers.showImagePicker
import ie.wit.citizenscience.models.SightingModel
import ie.wit.citizenscience.ui.auth.LoggedInViewModel
import ie.wit.citizenscience.ui.sightingslist.SightingListViewModel
import timber.log.Timber

class SightingDetailFragment : Fragment() {

    private lateinit var detailViewModel: SightingDetailViewModel
    private val args by navArgs<SightingDetailFragmentArgs>()
    private var _fragBinding: FragmentSightingDetailBinding? = null
    private val fragBinding get() = _fragBinding!!
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    private val sightingListViewModel : SightingListViewModel by activityViewModels()
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    var sighting = SightingModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.sighting_detail_fragment, container, false)

        //Toast.makeText(context,"Sighting ID Selected : ${args.sightingid}",Toast.LENGTH_LONG).show()

        //return view

        _fragBinding = FragmentSightingDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        fragBinding.editSightingButton.setOnClickListener {
            detailViewModel.updateSighting(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                args.sightingid, fragBinding.sightingvm?.observableSighting!!.value!!)
            findNavController().navigateUp()
        }

        fragBinding.deleteSightingButton.setOnClickListener {
            sightingListViewModel.delete(loggedInViewModel.liveFirebaseUser.value?.uid!!,
                detailViewModel.observableSighting.value?.uid!!)
            findNavController().navigateUp()
        }

        fragBinding.updateImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
                    Timber.i("Select image")
        }

        detailViewModel = ViewModelProvider(this).get(SightingDetailViewModel::class.java)

        detailViewModel.observableSighting.observe(viewLifecycleOwner, Observer { render() })

        registerImagePickerCallback()

        return root
    }



    private fun render(/*sighting: SightingModel*/) {

        fragBinding.sightingvm = detailViewModel

    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getSighting(loggedInViewModel.liveFirebaseUser.value?.uid!!,
            args.sightingid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    Activity.RESULT_OK -> {
                        if (result.data != null) {
                            Timber.i("Got Result ${result.data!!.data}")
                            _fragBinding?.sightingImage?.let {
                                FirebaseImageManager
                                    //.updateSightingImage(loggedInViewModel.liveFirebaseUser.value!!.uid,
                                    .updateSightingImage(sighting.uid!!,
                                        readImageUri(result.resultCode, result.data),
                                        it,
                                        true)
                            }
/*
                            i("Got Result ${result.data!!.data}")
                            sighting.image = result.data!!.data!!.toString()

                            Picasso.get()
                                //.load(sighting.image)
                                .load(sighting.image.toUri())
                                .transform(customTransformation())
                                .into(_fragBinding?.sightingImage)

*/
                            //binding.chooseImage.setText((R.string.button_updateImage))
                        } // end of if
                    }
                    Activity.RESULT_CANCELED -> { } else -> { }
                }
            }
    }

}