package ie.wit.citizenscience.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.FragmentSightingDetailBinding

class SightingDetailFragment : Fragment() {

    private lateinit var detailViewModel: SightingDetailViewModel
    private val args by navArgs<SightingDetailFragmentArgs>()
    private var _fragBinding: FragmentSightingDetailBinding? = null
    private val fragBinding get() = _fragBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.sighting_detail_fragment, container, false)

        //Toast.makeText(context,"Sighting ID Selected : ${args.sightingid}",Toast.LENGTH_LONG).show()

        //return view

        _fragBinding = FragmentSightingDetailBinding.inflate(inflater, container, false)
        val root = fragBinding.root

        detailViewModel = ViewModelProvider(this).get(SightingDetailViewModel::class.java)
        detailViewModel.observableSighting.observe(viewLifecycleOwner, Observer { render() })
        return root
    }

    private fun render(/*sighting: SightingModel*/) {

        fragBinding.sightingvm = detailViewModel
    }

    override fun onResume() {
        super.onResume()
        detailViewModel.getSighting(args.sightingid)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

}