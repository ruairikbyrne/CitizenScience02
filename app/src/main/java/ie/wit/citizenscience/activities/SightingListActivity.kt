package ie.wit.citizenscience.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ie.wit.citizenscience.main.MainApp
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.citizenscience.R
import ie.wit.citizenscience.adapters.SightingAdapter
import ie.wit.citizenscience.adapters.SightingListener
import ie.wit.citizenscience.databinding.ActivitySightingListBinding
import ie.wit.citizenscience.models.SightingModel

class SightingListActivity : AppCompatActivity(), SightingListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivitySightingListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySightingListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager =  LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = SightingAdapter(app.sightings.findAll(), this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, SightingActivity::class.java)
                startActivityForResult(launcherIntent,0)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSightingClick(sighting: SightingModel) {
        val launcherIntent = Intent(this, SightingActivity::class.java)
        launcherIntent.putExtra("sighting_edit", sighting)
        startActivityForResult(launcherIntent,0)
    }
}

