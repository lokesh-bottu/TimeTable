package com.example.timetable


import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.close
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.timetable.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout : DrawerLayout = findViewById(R.id.drawerLayout)
        val navView :NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_login -> {
                    val intent = Intent(this,SignUpActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_home -> {
                    Toast.makeText(applicationContext, "Clicked Home", Toast.LENGTH_LONG).show()
                    true
                }
                else -> false
            }
        }
        binding.mon.setOnClickListener{
            replaceFragment(monday())
        }


        binding.tue.setOnClickListener{
            replaceFragment(tuesday())
        }

        binding.wed.setOnClickListener{
            replaceFragment(wednesday())
        }


        binding.thur.setOnClickListener{
            replaceFragment(thursday())
        }


        binding.fri.setOnClickListener{
            replaceFragment(friday())
        }
    }



    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.addToBackStack(null) // Add the transaction to the back stack
        fragmentTransaction.commit()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}