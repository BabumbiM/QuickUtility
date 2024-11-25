package com.example.quickutility


import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the toolbar as the action bar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize the bottom navigation view
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Load the default fragment (Calendar) when the app starts
        if (savedInstanceState == null) {
            loadFragment(CalendarFragment())
        }

        // Handle bottom navigation item selection
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_calendar -> {
                    loadFragment(CalendarFragment())
                    true
                }
                R.id.nav_calculator -> {
                    loadFragment(CalculatorFragment())
                    true
                }
                R.id.nav_converter -> {
                    loadFragment(ConverterFragment())
                    true
                }
                R.id.nav_flash_events -> {
                    loadFragment(FlashEventsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu) // Inflate the menu_main.xml
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                showAppearanceDialog()
                return true
            }
            R.id.action_help -> {
                showHelpDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAppearanceDialog() {
        val options = arrayOf("Light Theme", "Dark Theme")
        AlertDialog.Builder(this)
            .setTitle("Choose Appearance")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> Toast.makeText(this, "Light Theme selected!", Toast.LENGTH_SHORT).show()
                    1 -> Toast.makeText(this, "Dark Theme selected!", Toast.LENGTH_SHORT).show()
                }
            }
            .show()
    }

    private fun showHelpDialog() {
        AlertDialog.Builder(this)
            .setTitle("Help")
            .setMessage(
                "Welcome to QuickUtility!\n\n" +
                        "1. Select a category from the navigation bar.\n" +
                        "2. Use the available tools like Calendar, Calculator, Converter, or Flash Events.\n" +
                        "3. Access settings for appearance preferences or contact support via Help.\n\n" +
                        "For further assistance, feel free to contact support."
            )
            .setPositiveButton("Got it") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
