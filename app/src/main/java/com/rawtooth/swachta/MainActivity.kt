package com.rawtooth.swachta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import com.easyvolley.NetworkClient
import com.rawtooth.swachta.databinding.ActivityMainBinding
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener


class MainActivity : AppCompatActivity() {
    lateinit var toggle:ActionBarDrawerToggle
    lateinit var mainBinding:ActivityMainBinding
    var imageArray:ArrayList<Int> = ArrayList()
    var carsualView:CarouselView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        setSupportActionBar(mainBinding.topAppBar)
       toggle= ActionBarDrawerToggle(this,mainBinding.mainDrawer,R.string.open,R.string.close)
        mainBinding.mainDrawer.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        NetworkClient.init(application)
        imageArray.add(R.drawable.img3)
        imageArray.add(R.drawable.recycle)
        imageArray.add(R.drawable.navigationimg)
        imageArray.add(R.drawable.img4)
        imageArray.add(R.drawable.img5)
        imageArray.add(R.drawable.img6)
        imageArray.add(R.drawable.img7)

        carsualView=mainBinding.carouselView
        carsualView!!.pageCount=imageArray.size
        carsualView!!.setImageListener(imageListner)
    }
    var imageListner=ImageListener{position, imageView -> imageView.setImageResource(imageArray[position]) }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar2,menu)
        return super.onCreateOptionsMenu(menu)
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