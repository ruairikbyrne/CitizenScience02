package ie.wit.citizenscience.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ie.wit.citizenscience.R
import ie.wit.citizenscience.databinding.HomeBinding
import androidx.navigation.*
import androidx.navigation.ui.*
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso
import ie.wit.citizenscience.databinding.NavHeaderBinding
import ie.wit.citizenscience.helpers.customTransformation
import ie.wit.citizenscience.ui.auth.LoggedInViewModel
import ie.wit.citizenscience.ui.auth.Login
import timber.log.Timber.i

class Home : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var homeBinding : HomeBinding
    private lateinit var navHeaderBinding : NavHeaderBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loggedInViewModel : LoggedInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        homeBinding = HomeBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)
        drawerLayout = homeBinding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.sightingFragment, R.id.sightingListFragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = homeBinding.navView
        navView.setupWithNavController(navController)
        //setContentView(R.layout.home)

    }

    public override fun onStart() {
        super.onStart()
        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel.liveFirebaseUser.observe(this, Observer { firebaseUser ->
            if (firebaseUser != null) {
                //val currentUser = loggedInViewModel.liveFirebaseUser.value
                /*if (currentUser != null)*/ updateNavHeader(loggedInViewModel.liveFirebaseUser.value!!)
            }
        })

        loggedInViewModel.loggedOut.observe(this, Observer { loggedout ->
            if (loggedout) {
                startActivity(Intent(this, Login::class.java))
            }
        })

    }

    private fun updateNavHeader(currentUser: FirebaseUser) {
        var headerView = homeBinding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderBinding.bind(headerView)
        navHeaderBinding.navHeaderName.text = currentUser.displayName
        navHeaderBinding.navHeaderEmail.text = currentUser.email
        if(currentUser.photoUrl != null && currentUser.displayName != null) {
            navHeaderBinding.navHeaderName.text = currentUser.displayName
            Picasso.get().load(currentUser.photoUrl)
                .resize(200, 200)
                .transform(customTransformation())
                .centerCrop()
                .into(navHeaderBinding.imageView)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut()
        //Launch Login activity and clear the back stack to stop navigating back to the Home activity
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}