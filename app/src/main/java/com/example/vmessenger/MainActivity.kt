package com.example.vmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.vmessenger.Profile.ProfileActivity
import com.example.vmessenger.databinding.ActivityMainBinding
import com.example.vmessenger.fragments.ChatFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    var actionBarDrawerToggle: ActionBarDrawerToggle?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController=findNavController(R.id.mainFragment)
        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)
     // supportFragmentManager.beginTransaction().replace(R.id.mac, ChatFragment()).commit()
        actionBarDrawerToggle= ActionBarDrawerToggle(this,binding.mac,R.string.open,R.string.close)
        binding.mac.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.myNevigationView.setNavigationItemSelectedListener(this)


    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.rateus->{
                Toast.makeText(this,"Rate us", Toast.LENGTH_SHORT).show()
            }
            R.id.ppolicy->{
                Toast.makeText(this,"privacy policy", Toast.LENGTH_SHORT).show()
            }
            R.id.shareApp->{
                Toast.makeText(this,"Share App", Toast.LENGTH_SHORT).show()
            }
            R.id.tc->{
                Toast.makeText(this,"terms and conditions", Toast.LENGTH_SHORT).show()
            }
            R.id.psetting->{
                startActivity(Intent(this,ProfileActivity::class.java))
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(actionBarDrawerToggle!!.onOptionsItemSelected(item)) return true
        else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if(binding.mac.isDrawerOpen(GravityCompat.START)){
            binding.mac.close()
        }
        else super.onBackPressed()
    }
}