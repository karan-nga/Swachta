package com.rawtooth.swachta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.rawtooth.swachta.databinding.ActivityMainBinding





class MainActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var mainBinding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        setSupportActionBar(mainBinding.topAppBar)
       toggle= ActionBarDrawerToggle(this,mainBinding.mainDrawer,R.string.open,R.string.close)
        mainBinding.mainDrawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    fun register(menuItem: MenuItem){
        startActivity(Intent(this,register::class.java))
    }
    fun login(menuItem: MenuItem){
        startActivity(Intent(this,Login::class.java))
    }

}