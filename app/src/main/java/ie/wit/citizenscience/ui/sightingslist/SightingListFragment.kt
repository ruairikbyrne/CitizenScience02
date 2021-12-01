package ie.wit.citizenscience.ui.sightingslist

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.citizenscience.R
//import ie.wit.citizenscience.activities.SightingActivity
import ie.wit.citizenscience.adapters.SightingAdapter
//import ie.wit.citizenscience.adapters.SightingListener
import ie.wit.citizenscience.databinding.FragmentSightingListBinding
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingModel


class SightingListFragment : Fragment() /*SightingListener, MultiplePermissionsListener*/ {


    private var _fragBinding: FragmentSightingListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var sightingListViewModel: SightingListViewModel

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = activity?.application as MainApp

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _fragBinding = FragmentSightingListBinding.inflate(inflater, container, false)
        val root = fragBinding.root
        activity?.title = getString(R.string.action_reported_sightings)

        fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))

        sightingListViewModel = ViewModelProvider(this).get(SightingListViewModel::class.java)
        sightingListViewModel.observableSightingsList.observe(viewLifecycleOwner, Observer {
                sightings ->
            sightings?.let { render(sightings) }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = SightingListFragmentDirections.actionSightingListFragmentToSightingFragment()
            findNavController().navigate(action)
        }



        //fragBinding.recyclerView.adapter = SightingAdapter(app.sightings.findAll())
        //loadSightings()
        //registerRefreshCallback()
        //registerMapCallback()
        return root;

        //return inflater.inflate(R.layout.fragment_sighting_list, container, false)
    }

    override fun onResume() {
        super.onResume()

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SightingListFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_sightinglist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item,
            requireView().findNavController()) || super.onOptionsItemSelected(item)
    }
/*
    override fun onSightingClick(sighting: SightingModel) {

        val launcherIntent = Intent(requireContext(), SightingActivity::class.java)
        launcherIntent.putExtra("sighting_edit", sighting)
        refreshIntentLauncher.launch(launcherIntent)
    }

 */
/*
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadSightings() }
    }
*/
    /*
    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

    private fun loadSightings() {
        showSightings(app.sightings.findAll())
    }

    fun showSightings (sightings: List<SightingModel>) {
        fragBinding.recyclerView.adapter = SightingAdapter(sightings, this)
        fragBinding.recyclerView.adapter?.notifyDataSetChanged()
    }
*/
    private fun render(sightingsList: List<SightingModel>) {
        fragBinding.recyclerView.adapter = SightingAdapter(sightingsList)
        if (sightingsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.sightingsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.sightingsNotFound.visibility = View.GONE
        }
    }


}