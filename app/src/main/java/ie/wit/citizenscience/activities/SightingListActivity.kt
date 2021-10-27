package ie.wit.citizenscience.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.citizenscience.main.MainApp
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.citizenscience.R
import ie.wit.citizenscience.adapters.SightingAdapter
import ie.wit.citizenscience.adapters.SightingListener
import ie.wit.citizenscience.databinding.ActivitySightingListBinding
import ie.wit.citizenscience.models.SightingModel
import timber.log.Timber.i
import java.util.*

class SightingListActivity : AppCompatActivity(), SightingListener/*, MultiplePermissionsListener*/ {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySightingListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var mapIntentLauncher : ActivityResultLauncher<Intent>

    var sightinglisting: MutableList<SightingModel> = mutableListOf()
    var matchedSightings: MutableList<SightingModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySightingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager =  LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadSightings()

        binding.searchView.isSubmitButtonEnabled = true
        //performSearch()

        registerRefreshCallback()
        registerMapCallback()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val menuItem = menu!!.findItem(R.id.action_search)
        if (menuItem != null) {

            val searchView = menuItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {

                    if (newText!!.isNotEmpty()) {
                        matchedSightings.clear()
                        i("Found items $sightinglisting")
                        val search = newText.toLowerCase(Locale.getDefault())
                        sightinglisting.forEach {
                            if (it.classification.toLowerCase(Locale.getDefault()).contains(search) ||
                                    it.species.toLowerCase(Locale.getDefault()).contains(search)){
                                matchedSightings.add(it)
                            }
                        }
                        showFilteredSightings(matchedSightings)
                    }
                    else {
                        loadSightings()
                    }
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SightingActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
            R.id.item_map -> {
                val launcherIntent = Intent(this, SightingMapsActivity::class.java)
                mapIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSightingClick(sighting: SightingModel) {
        val launcherIntent = Intent(this, SightingActivity::class.java)
        launcherIntent.putExtra("sighting_edit", sighting)
        refreshIntentLauncher.launch(launcherIntent)
    }



/**
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }
**/

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadSightings() }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {  }
    }

    private fun loadSightings() {
        showSightings(app.sightings.findAll())

    }

    fun showSightings (sightings: List<SightingModel>) {
        binding.recyclerView.adapter = SightingAdapter(sightings, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
        sightinglisting.clear()
        sightinglisting.addAll(app.sightings.findAll())

    }

    fun showFilteredSightings (sightings: List<SightingModel>) {

        binding.recyclerView.adapter = SightingAdapter(matchedSightings, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

