package ie.wit.citizenscience.ui.sightingslist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ie.wit.citizenscience.R
//import ie.wit.citizenscience.activities.SightingActivity
import ie.wit.citizenscience.adapters.SightingAdapter
import ie.wit.citizenscience.adapters.SightingClickListener
//import ie.wit.citizenscience.adapters.SightingListener
import ie.wit.citizenscience.databinding.FragmentSightingListBinding
import ie.wit.citizenscience.helpers.createLoader
import ie.wit.citizenscience.helpers.hideLoader
import ie.wit.citizenscience.helpers.showLoader
import ie.wit.citizenscience.main.MainApp
import ie.wit.citizenscience.models.SightingModel
import ie.wit.citizenscience.ui.auth.LoggedInViewModel
import ie.wit.citizenscience.utils.SwipeToDeleteCallback
import ie.wit.citizenscience.utils.SwipeToEditCallback


class SightingListFragment : Fragment(), SightingClickListener /*, MultiplePermissionsListener*/ {


    private var _fragBinding: FragmentSightingListBinding? = null
    private val fragBinding get() = _fragBinding!!
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>
    //private lateinit var sightingListViewModel: SightingListViewModel
    private val sightingListViewModel: SightingListViewModel by activityViewModels()
    private val loggedInViewModel : LoggedInViewModel by activityViewModels()
    lateinit var loader : AlertDialog

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
        loader = createLoader(requireActivity())
        activity?.title = getString(R.string.action_reported_sightings)

        //fragBinding.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        fragBinding.recyclerView.layoutManager = LinearLayoutManager(activity)

        //sightingListViewModel = ViewModelProvider(this).get(SightingListViewModel::class.java)
        showLoader(loader,"Downloading Sightings")
        sightingListViewModel.observableSightingsList.observe(viewLifecycleOwner, Observer {
                sightings ->
            sightings?.let {
                render(sightings as ArrayList<SightingModel>)
                hideLoader(loader)
                checkSwipeRefresh()
            }
        })

        val fab: FloatingActionButton = fragBinding.fab
        fab.setOnClickListener {
            val action = SightingListFragmentDirections.actionSightingListFragmentToSightingFragment()
            findNavController().navigate(action)
        }

        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                showLoader(loader, "Deleting Sighting")
                val adapter = fragBinding.recyclerView.adapter as SightingAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                sightingListViewModel.delete(sightingListViewModel.liveFirebaseUser.value?.uid!!,
                (viewHolder.itemView.tag as SightingModel).uid!!)
                hideLoader(loader)

            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(fragBinding.recyclerView)

        val swipeEditHandler = object : SwipeToEditCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onSightingClick(viewHolder.itemView.tag as SightingModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(fragBinding.recyclerView)

        //fragBinding.recyclerView.adapter = SightingAdapter(app.sightings.findAll())
        //loadSightings()
        //registerRefreshCallback()
        //registerMapCallback()
        //return inflater.inflate(R.layout.fragment_sighting_list, container, false)
        return root;


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

        /*val launcherIntent = Intent(requireContext(), SightingActivity::class.java)
        launcherIntent.putExtra("sighting_edit", sighting)
        refreshIntentLauncher.launch(launcherIntent)*/
    }



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

    fun setSwipeRefresh() {
        fragBinding.swiperefresh.setOnRefreshListener {
            fragBinding.swiperefresh.isRefreshing = true
            showLoader(loader,"Downloading Donations")
            sightingListViewModel.load()
        }
    }

    fun checkSwipeRefresh() {
        if (fragBinding.swiperefresh.isRefreshing)
            fragBinding.swiperefresh.isRefreshing = false
    }

    private fun render(sightingsList: ArrayList<SightingModel>) {
        fragBinding.recyclerView.adapter = SightingAdapter(sightingsList,this)
        if (sightingsList.isEmpty()) {
            fragBinding.recyclerView.visibility = View.GONE
            fragBinding.sightingsNotFound.visibility = View.VISIBLE
        } else {
            fragBinding.recyclerView.visibility = View.VISIBLE
            fragBinding.sightingsNotFound.visibility = View.GONE
        }
    }

    override fun onSightingClick(sighting: SightingModel) {
        val action = SightingListFragmentDirections.actionSightingListFragmentToSightingDetailFragment(sighting.uid!!)
        findNavController().navigate(action)
    }


}