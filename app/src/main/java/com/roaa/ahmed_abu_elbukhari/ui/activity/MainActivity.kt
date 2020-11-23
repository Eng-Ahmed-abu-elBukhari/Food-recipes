package com.roaa.ahmed_abu_elbukhari.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.google.android.gms.ads.MobileAds
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.roaa.ahmed_abu_elbukhari.R
import com.roaa.ahmed_abu_elbukhari.data.utils.RateApp
import com.roaa.ahmed_abu_elbukhari.databinding.ActivityMainBinding
import com.roaa.ahmed_abu_elbukhari.databinding.NavHeaderMainBinding
import com.roaa.ahmed_abu_elbukhari.ui.viewmodel.MainViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{


    private lateinit var mainViewModel: MainViewModel
    private lateinit var headerBinding : NavHeaderMainBinding
    private val activityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(
            this, R.layout.activity_main
        )
    }


    //1
    /**You’ll use navController to navigate from one fragment to another.
     * Import findNavController from androidx.navigation.findNavController. */
    private val navController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    //2
    /** appBarConfiguration defines which fragments are the top level fragments so the drawerLayout
     * and hamburger icon can work properly. You’ll understand why when you run the app.*/
    private val appBarConfiguration by lazy {

        AppBarConfiguration(
            setOf(
                R.id.homeFragment
                //,R.id.settingsFragment
            ), drawerLayout = drawerLayout
        )
    }

    private lateinit var mAdView : AdView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        setupDataBinding()
        setSupportActionBar(toolbar)
        setupNavigation()
        setupViewModel()
        setupViews()


        // ads
        buildBannerAds()
        settingBannerAds()

    }


    private fun setupNavigation() {
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration)


        navController.addOnDestinationChangedListener { nc, destination, _ ->
            if (destination.id in arrayOf(
                    R.id.searchFragment,
                    R.id.settingsFragment,
                    R.id.categoryFragment,
                    R.id.detailsFragment
                )
            ) {
                fab.hide()
            } else {
                fab.show()
            }


            if (destination.id == R.id.searchFragment || destination.id == R.id.detailsFragment ) {
                toolbar.visibility = View.GONE
            } else{
                toolbar.visibility = View.VISIBLE
            }

            /**
            if (destination.id == R.id.categoryFragment){
                toolbar.setBackgroundColor(Color.WHITE)
            }else{
                toolbar.setBackgroundColor(ContextCompat.getColor(this,
                    R.color.colorPrimary,))
            }

*/
            // prevent nav gesture if not on start destination
            if (destination.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
            }
    }


    private fun setupViews() {
        navView.setNavigationItemSelectedListener(this)
        fab.setOnClickListener {
            val extras = FragmentNavigatorExtras(start_view to "shared_element_container")
            navController.navigate(R.id.searchFragment, null, null, extras)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
      return navController.navigateUp(appBarConfiguration) ||  super.onSupportNavigateUp()
       // return NavigationUI.navigateUp(navController, drawerLayout)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> {
                navController.popBackStack(R.id.homeFragment, false)
            }
            R.id.nav_setting -> {
                navController.navigate(R.id.settingsFragment)
            }
            R.id.nav_rateApp->{
                RateApp(this).rateMyApp()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }




    private fun setupDataBinding() {
        headerBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.nav_header_main, activityMainBinding.navView, false
        )
        activityMainBinding.navView.addHeaderView(headerBinding.root)
    }





    private fun setupViewModel(){
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        try {
            val viewModelProvider = ViewModelProvider(
                navController.getViewModelStoreOwner(R.id.nav_graph),
                ViewModelProvider.AndroidViewModelFactory(application)
            )
            mainViewModel = viewModelProvider.get(MainViewModel::class.java)
            //   headerBinding.viewModel = lettersViewModel
            // lettersViewModel?.loadProfile()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }


    }


    private fun settingBannerAds(){
        val adView = AdView(this)
        adView.adSize = AdSize.SMART_BANNER
        adView.adUnitId = "ca-app-pub-2511142364873147/1517037161"
    }

    private fun buildBannerAds(){
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }



}