package ie.wit.citizenscience.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.citizenscience.main.MainApp
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.citizenscience.R
import ie.wit.citizenscience.adapters.SightingAdapter
import ie.wit.citizenscience.adapters.SightingListener
import ie.wit.citizenscience.databinding.ActivitySightingListBinding
import ie.wit.citizenscience.models.SightingModel

class SightingListActivity : AppCompatActivity(), SightingListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySightingListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>


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

        registerRefreshCallback()

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SightingActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSightingClick(sighting: SightingModel) {
        val launcherIntent = Intent(this, SightingActivity::class.java)
        launcherIntent.putExtra("sighting_edit", sighting)
        refreshIntentLauncher.launch(launcherIntent)
    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.recyclerView.adapter?.notifyDataSetChanged()
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadSightings() }
    }

    private fun loadSightings() {
        showSightings(app.sightings.findAll())
    }

    fun showSightings (sightings: List<SightingModel>) {
        binding.recyclerView.adapter = SightingAdapter(sightings, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}

