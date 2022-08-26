package com.rawtooth.swachta.schedule

import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import com.easyvolley.Callback
import com.easyvolley.EasyVolleyError
import com.easyvolley.EasyVolleyResponse
import com.easyvolley.NetworkClient
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.Gson
import com.rawtooth.swachta.*
import com.rawtooth.swachta.R
import com.rawtooth.swachta.databinding.ActivityFetchLocationBinding
import java.util.*

class FetchLocation : FragmentActivity(),OnMapReadyCallback,DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    lateinit var binding: ActivityFetchLocationBinding
    var currentLocation: Location? = null
    private var mLastKnownLocation: Location? = null
    var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mapView: View? = null
    private var mMap: GoogleMap? = null
    private var locationCallback: LocationCallback? = null
    val REQUEST_CODE = 101
    var pickdate: Button? = null
    var day = 0;var month:Int = 0;var year:Int = 0; var hours:Int = 0;var minute:Int = 0
    var dayFinal = 0; var monthFinal:Int = 0; var yearFinal:Int = 0;var hoursFinal:Int = 0; var minuteFinal:Int = 0
    var dateFinal: String? = null; var timeFinal:kotlin.String? = null
    lateinit var tokenManager: TokenManager

    companion object {
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFetchLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val message = intent.getStringExtra("message_key")
        binding.wasteDeatils.setText(message)


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mapView = mapFragment.view

        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(this)
        binding.getaddress.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    applicationContext, android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_REQUEST_CODE
                )
            } else {
                binding.loader.visibility = View.VISIBLE
                getAddress()
            }

        }
        tokenManager= TokenManager(this)
        binding.finalDoneSchedule.setOnClickListener{
            finalpickUpSchedule()
        }


    }

    private fun finalpickUpSchedule() {
        val waste=binding.wasteDeatils.text.toString()
        val addresss=binding.tvAddress.text.toString()
        val time=" "
        val date=binding.dateTV.text.toString()
        if(!waste.isEmpty()&&!addresss.isEmpty()&&!date.isEmpty()){
            binding.finalDoneSchedule.isVisible=false
            binding.apidata.isVisible=true
            Toast.makeText(this@FetchLocation,"Please wait",Toast.LENGTH_LONG).show()
            onCheck(waste,addresss,time,date, uid)
        }
        else{
            Toast.makeText(this,"Please enter all details",Toast.LENGTH_SHORT).show()
            binding.finalDoneSchedule.isVisible=true
            binding.apidata.isVisible=false
        }

    }

    private fun onCheck(waste: String, addresss: String, time: String, date: String, uid: String?,) {
        tokenManager=TokenManager(this)
        val body= Gson().toJson(ScheduleModel(date,addresss,time, "1",waste))
        NetworkClient.post("${Constant.baseurl}waste/schedule-pickUp")
            .addHeader("Content-Type", "application/json")
            .addHeader("Content-Length", Integer.toString(body.length))
            .addHeader("Authorization", "Bearer ${tokenManager.getToken()}")
            .addHeader("Accept", "application/json")
            .setRequestBody(body)
            .setCallback(object :Callback<String>{
                override fun onSuccess(t: String?, response: EasyVolleyResponse?) {
                    if(response!=null){
                        Log.d("code",response.toString())
                        startActivity(Intent(this@FetchLocation,SuccessScreenPickup::class.java))
                    }
                }

                override fun onError(error: EasyVolleyError?) {
                    Log.e("code",error.toString())
                    startActivity(Intent(this@FetchLocation,SuccessScreenPickup::class.java))
                }

            }).execute()
    }

    private fun getAddress() {
        var locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //now getting address from latitude and longitude

        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses:List<Address>

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest,object :LocationCallback(){
                override fun onLocationResult(locationResult: LocationResult) {
                    if (locationResult != null) {
                        super.onLocationResult(locationResult)
                    }
                    LocationServices.getFusedLocationProviderClient(this@FetchLocation)
                        .removeLocationUpdates(this)
                    if (locationResult != null && locationResult.locations.size > 0){
                        var locIndex = locationResult.locations.size-1
                        var latitude = locationResult.locations.get(locIndex).latitude
                        var longitude = locationResult.locations.get(locIndex).longitude
                        addresses = geocoder.getFromLocation(latitude,longitude,1)!!

                        var address:String = addresses[0].getAddressLine(0)
                        binding.tvAddress.setText(address)
                        if (binding.tvAddress != null){
                            binding.loader.visibility = View.GONE
                        }
                    }
                }
            }, Looper.getMainLooper())
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mMap!!.setMyLocationEnabled(true)
        mMap!!.getUiSettings().setMyLocationButtonEnabled(true)

        if (mapView != null && mapView!!.findViewById<View?>("1".toInt()) != null) {
            val locationButton =
                (mapView!!.findViewById<View>("1".toInt()).parent as View).findViewById<View>("2".toInt())
            val layoutParams = locationButton.layoutParams as RelativeLayout.LayoutParams
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
            layoutParams.setMargins(0, 0, 40, 180)
        }

        //check if gps is enabled or not and then request user to enable it

        //check if gps is enabled or not and then request user to enable it
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

        val settingsClient = LocationServices.getSettingsClient(this)
        val task = settingsClient.checkLocationSettings(builder.build())
        task.addOnSuccessListener(this,
            OnSuccessListener { locationSettingsResponse: LocationSettingsResponse? -> getDeviceLocati() })

        task.addOnFailureListener(
            this
        ) { e ->
            if (e is ResolvableApiException) {
                val resolvable = e as ResolvableApiException
                try {
                    resolvable.startResolutionForResult(this, 51)
                } catch (e1: IntentSender.SendIntentException) {
                    e1.printStackTrace()
                }
            }
        }


    }

    fun getDeviceLocati() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        mFusedLocationProviderClient!!.lastLocation
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mLastKnownLocation = task.result
                    if (mLastKnownLocation != null) {
                        mMap!!.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    mLastKnownLocation!!.getLatitude(),
                                    mLastKnownLocation!!.getLongitude()
                                ), 50F
                            )
                        )
                    } else {
                        val locationRequest = LocationRequest.create()
                        locationRequest.interval = 10000
                        locationRequest.fastestInterval = 5000
                        locationRequest.priority =
                            LocationRequest.PRIORITY_HIGH_ACCURACY
                        locationCallback = object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                if (locationResult == null) {
                                    return
                                }
                                mLastKnownLocation = locationResult.lastLocation
                                mMap!!.moveCamera(
                                    CameraUpdateFactory.newLatLngZoom(
                                        LatLng(
                                            mLastKnownLocation!!.getLatitude(),
                                            mLastKnownLocation!!.getLongitude()
                                        ), 50F
                                    )
                                )
                                locationCallback?.let {
                                    mFusedLocationProviderClient!!.removeLocationUpdates(
                                        it
                                    )
                                }
                            }
                        }
                        mFusedLocationProviderClient!!.requestLocationUpdates(
                            locationRequest,
                            locationCallback as LocationCallback,
                            null
                        )
                    }
                } else {
                    Toast.makeText(
                        this,
                        "unable to get last location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
               getAddress()
            }else{
                Toast.makeText(this,"Permission Denied!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        yearFinal = year
        monthFinal = month + 1
        dayFinal = dayOfMonth

        val c = Calendar.getInstance()
        hours = c[Calendar.HOUR_OF_DAY]
        minute = c[Calendar.MINUTE]

        val timePickerDialog = TimePickerDialog(
            this@FetchLocation, this@FetchLocation,
            hours, minute, DateFormat.is24HourFormat(this)
        )
        timePickerDialog.show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, mintue: Int) {
        hoursFinal = hourOfDay
        minuteFinal = minute
        dateFinal = "$dayFinal/$monthFinal/$yearFinal"
        timeFinal = "$hoursFinal:$minuteFinal"
        binding.dateTV.setText("Scheduling Pickup at $hoursFinal:$minuteFinal, on $dayFinal/$monthFinal/$yearFinal.")
    }

    fun selectDate(view: View) {
        val c = Calendar.getInstance()
        year = c[Calendar.YEAR]
        month = c[Calendar.MONTH]
        day = c[Calendar.DAY_OF_MONTH]

        val datePickerDialog =
            DatePickerDialog(this@FetchLocation, this@FetchLocation, year, month, day)
        datePickerDialog.show()
    }

}




