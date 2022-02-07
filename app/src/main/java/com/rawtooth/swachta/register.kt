package com.rawtooth.swachta

import android.content.Intent
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
import com.google.gson.Gson


class register : AppCompatActivity(), View.OnClickListener {
   var value:Int = 0
    lateinit var response: EasyVolleyResponse
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

        val body = Gson().toJson(RegisterPost(name,email,pass,contact))
        NetworkClient.post("http://192.168.1.95:9090/user/")
            .addHeader("Content-Type", "application/json")
            .addHeader("Content-Length", Integer.toString(body.length))
            .addHeader("Accept", "application/json")
            .setRequestBody(body)
            .setCallback(object : Callback<String> {
                override fun onSuccess(t: String?, response: EasyVolleyResponse?) {
                    Log.d("code",  t.toString())
                    if(response!=null){
                    if(response.mStatusCode==200){
              Toast.makeText(this@register,"Sucessfull",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@register,Login::class.java))
            }
            else if(response.mStatusCode==500){
                Toast.makeText(this@register, "Your are already registered", Toast.LENGTH_SHORT).show()
               startActivity(Intent(this@register,MainActivity::class.java))
            }
            else{
                Toast.makeText(this@register, "Request Failed", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@register,MainActivity::class.java))
            }
                        }




                    }

                override fun onError(error: EasyVolleyError?) {
                    value=error!!.mStatusCode
                        Log.e("code", " Error" + error!!.mStatusCode.toString())
                    if(response.mStatusCode==500){
                        Toast.makeText(this@register, "Your are already registered", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@register,MainActivity::class.java))
                    }

                    };



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
            onSave(name, email, contact, pass)
            registerBind.loginscreen.text=value.toString()
//            if(value==200){
//                Toast.makeText(this, "Successfully registered", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this,Login::class.java))
//            }
//            else if(value==500){
//                Toast.makeText(this, "Your are already registered", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this,Login::class.java))
//            }
//            else{
//                Toast.makeText(this, "Request Failed", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this,MainActivity::class.java))
//            }


        } else {
            Toast.makeText(this, "Enter valid data", Toast.LENGTH_SHORT).show()
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