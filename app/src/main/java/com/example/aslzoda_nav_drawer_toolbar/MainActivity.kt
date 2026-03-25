package com.example.aslzoda_nav_drawer_toolbar

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.aslzoda_nav_drawer_toolbar.databinding.ActivityMainBinding
import com.example.aslzoda_nav_drawer_toolbar.ui.about.AboutFragment
import com.example.aslzoda_nav_drawer_toolbar.ui.add.AddTaskFragment
import com.example.aslzoda_nav_drawer_toolbar.ui.home.HomeFragment
import com.example.aslzoda_nav_drawer_toolbar.ui.profile.EditProfileFragment
import com.example.aslzoda_nav_drawer_toolbar.ui.settings.SettingsFragment
import com.example.aslzoda_nav_drawer_toolbar.ui.stats.StatsFragment
import com.example.aslzoda_nav_drawer_toolbar.utils.ImageUtils
import com.example.aslzoda_nav_drawer_toolbar.utils.PrefManager
import java.io.File
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(),
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding


    private lateinit var headerImage: ImageView
    private lateinit var headerName: TextView
    private lateinit var headerEmail: TextView


    private val imagePicker =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                val path = ImageUtils.saveToInternal(this, it)
                PrefManager.saveImage(this, path)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setupDrawer()
        setupHeader()
        observeHeaderLiveData()


        supportFragmentManager.addOnBackStackChangedListener {
            updateToolbarTitle()
        }

        if (savedInstanceState == null) {
            openFragment(HomeFragment(), false)
            binding.navView.setCheckedItem(R.id.home)
        }
    }


    private fun setupDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
    }


    private fun setupHeader() {
        val header = binding.navView.getHeaderView(0)
        headerImage = header.findViewById(R.id.profileImage)
        headerName = header.findViewById(R.id.profileName)
        headerEmail = header.findViewById(R.id.profileEmail)

        loadHeader()


        header.setOnClickListener {
            openFragment(EditProfileFragment())
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }


        headerImage.setOnClickListener {
            imagePicker.launch("image/*")
        }
    }


    private fun observeHeaderLiveData() {
        PrefManager.nameLiveData.observe(this) { name ->
            headerName.text = if (name.isNotEmpty()) name else "Your Name"
        }

        PrefManager.emailLiveData.observe(this) { email ->
            headerEmail.text = if (email.isNotEmpty()) email else "youremail@example.com"
        }

        PrefManager.imageLiveData.observe(this) { path ->
            path?.let {
                val file = File(it)
                if (file.exists()) headerImage.setImageURI(Uri.fromFile(file))
            }
        }
    }


    private fun loadHeader() {
        headerName.text = if (PrefManager.getName(this).isNotEmpty()) PrefManager.getName(this) else "Your Name"
        headerEmail.text = if (PrefManager.getEmail(this).isNotEmpty()) PrefManager.getEmail(this) else "youremail@example.com"
        PrefManager.getImage(this)?.let {
            val file = File(it)
            if (file.exists()) headerImage.setImageURI(Uri.fromFile(file))
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> openFragment(HomeFragment(), false)
            R.id.settings -> openFragment(SettingsFragment())
            R.id.nav_profile -> openFragment(EditProfileFragment())
            R.id.about -> openFragment(AboutFragment())
            R.id.stats -> openFragment(StatsFragment())
            R.id.add_task -> openFragment(AddTaskFragment())
            R.id.log_out -> showLogoutDialog()
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
        if (addToBackStack) transaction.addToBackStack(fragment::class.java.simpleName)
        transaction.commit()
    }


    private fun updateToolbarTitle() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        supportActionBar?.title = when (fragment) {
            is HomeFragment -> "Home"
            is SettingsFragment -> "Settings"
            is EditProfileFragment -> "Edit Profile"
            is AboutFragment -> "About"
            is StatsFragment -> "Stats"
            is AddTaskFragment -> "Add Task"
            else -> getString(R.string.app_name)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_search_menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.queryHint = "Search tasks..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(query: String?): Boolean {
                val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
                if (fragment is HomeFragment) fragment.search(query.orEmpty())
                return true
            }
        })
        return true
    }


    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Log out")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                PrefManager.saveName(this, "")
                PrefManager.saveEmail(this, "")
                PrefManager.saveImage(this, null)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }


    @SuppressLint("GestureBackNavigation")
    override fun onBackPressed() {
        when {
            binding.drawerLayout.isDrawerOpen(GravityCompat.START) -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            supportFragmentManager.backStackEntryCount > 0 -> {
                supportFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
                openFragment(HomeFragment(), false)
            }
            else -> super.onBackPressed()
        }
    }
}
