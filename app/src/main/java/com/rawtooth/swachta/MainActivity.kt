package com.rawtooth.swachta

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.browser.customtabs.CustomTabsIntent

import com.easyvolley.NetworkClient
import com.rawtooth.swachta.databinding.ActivityMainBinding
import com.rawtooth.swachta.schedule.NewPickup
import com.rawtooth.swachta.user.user_detail
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
        mainBinding.pickupCard.setOnClickListener{
            startActivity(Intent(this,NewPickup::class.java))
        }
        mainBinding.classify.setOnClickListener {
            val builder=CustomTabsIntent.Builder()
            val customTabsIntent=builder.build()
            customTabsIntent.launchUrl(this, Uri.parse("https://aniass-waste-app-waste-rkm4o1.streamlitapp.com/"))
        }
        mainBinding.dwtrw.setOnClickListener {
            val builder=CustomTabsIntent.Builder()
            val customTabsIntent=builder.build()
            customTabsIntent.launchUrl(this, Uri.parse("https://www.conserve-energy-future.com/simple-and-easy-ways-go-zero-waste-at-home.php"))
        }
    }
    var imageListner=ImageListener{position, imageView -> imageView.setImageResource(imageArray[position]) }



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
    fun home(menuItem: MenuItem){
        startActivity(Intent(this,MainActivity::class.java))
    }
    fun user(menuItem: MenuItem){
        startActivity(Intent(this,user_detail::class.java))
    }



}