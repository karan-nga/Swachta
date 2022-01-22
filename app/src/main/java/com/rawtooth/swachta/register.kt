package com.rawtooth.swachta

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.easyvolley.Callback

import com.easyvolley.EasyVolleyError
import com.easyvolley.EasyVolleyResponse
import com.easyvolley.NetworkClient
import com.rawtooth.swachta.databinding.ActivityRegisterBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.Gson


class register : AppCompatActivity(), View.OnClickListener {

    lateinit var registerBind: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBind.root)
        registerBind.registerbtn.setOnClickListener(this)
        val name = registerBind.username.text.toString().trim()
        val email = registerBind.email.text.toString().trim()
        val contact = registerBind.contact.text.toString().trim()
        val pass = registerBind.password.text.toString().trim()


    }
    //http://localhost:9090

    private fun onSave(name: String, email: String, contact: String, pass: String) {

        val body = Gson().toJson(Post(name,email,pass,contact))
        NetworkClient.post("http://192.168.254.251:9090/user/")
            .addHeader("Content-Type", "application/json")
            .addHeader("Content-Length", Integer.toString(body.length))
            .addHeader("Accept", "application/json")
            .setRequestBody(body)
            .setCallback(object : Callback<String> {
                override fun onSuccess(t: String?, response: EasyVolleyResponse?) {
                    Log.d("code",  t.toString());
                }

                override fun onError(error: EasyVolleyError?) {
                    Log.e("code", " Error" + error?.mMessage);
                }
            }
            ).execute()




//        val client = OkHttpClient.Builder()
//            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//            .build()
//        val retrofit = Retrofit.Builder().baseUrl("http://192.168.254.251:9090/").client(client)
//            .addConverterFactory(GsonConverterFactory.create()).build()
//        val jsonPlaceHolder = retrofit.create(localHost::class.java)
//        val userPost = Post(name, email, pass, contact)
//
//        val call = jsonPlaceHolder.sendUserData(userPost, body.length)
//        call.enqueue(object : Callback<Post> {
//            override fun onResponse(call: Call<Post>, response: Response<Post>) {
//                Log.d("code", response.body().toString())
//            }
//
//            override fun onFailure(call: Call<Post>, t: Throwable) {
//                Log.e("code", "FAIL", t)
//            }
//        })
    }


    override fun onClick(view: View?) {
        val name = registerBind.username.text.toString()
        val email = registerBind.email.text.toString()
        val contact = registerBind.contact.text.toString()
        val pass = registerBind.password.text.toString()
        val confPass = registerBind.confpassword.text.toString()
        val check: Boolean = vali(name, email, contact, pass, confPass)
        if (check) {
            Toast.makeText(applicationContext, "Data is valid", Toast.LENGTH_SHORT).show()
            onSave(name, email, contact, pass)

        } else {
            Toast.makeText(applicationContext, "Enter valid data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun vali(
        name: String,
        email: String,
        contact: String,
        pass: String,
        confPass: String
    ): Boolean {
        if (name.isEmpty()) {
            registerBind.username.requestFocus()
            registerBind.username.setError("Field cannot be empty")
            return false
        } else if (email.isEmpty()) {
            registerBind.email.requestFocus()
            registerBind.email.setError("Field cannot be empty")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerBind.email.requestFocus()
            registerBind.email.setError("Enter a valid email")
            return false

        } else if (contact.length < 10 || contact.length > 11 || contact == null) {
            registerBind.contact.requestFocus()
            registerBind.contact.setError("Contact number must be equal to 10")
            return false
        } else if (!Patterns.PHONE.matcher(contact).matches()) {
            registerBind.contact.requestFocus()
            registerBind.contact.setError("Enter valid contact details")
            return false
        } else if (pass.length < 5) {
            registerBind.password.requestFocus()
            registerBind.password.setError("Password must be greater than 5 characters")
            return false
        } else if (confPass != pass) {
            registerBind.confpassword.requestFocus()
            registerBind.confpassword.setError("Password not matching")
            return false
        } else {
            return true
        }

    }

}