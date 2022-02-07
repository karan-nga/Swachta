package com.rawtooth.swachta
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.easyvolley.Callback
import com.easyvolley.EasyVolleyError
import com.easyvolley.EasyVolleyResponse
import com.easyvolley.NetworkClient
import com.google.gson.Gson
import com.rawtooth.swachta.databinding.ActivityLoginBinding

class Login : AppCompatActivity(), View.OnClickListener {
    lateinit var loginBinding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        loginBinding.loginbtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val name=loginBinding.username.text.toString()
        val password=loginBinding.password.text.toString()
        onSave(name,password)
    }

    private fun onSave(name: String, password: String) {


            val body = Gson().toJson(Post2(name,password))
            NetworkClient.post("http://192.168.204.251:9090/generatetoken")
                .addHeader("Content-Type", "application/json")
                .addHeader("Content-Length", Integer.toString(body.length))
                .addHeader("Accept", "application/json")
                .setRequestBody(body)
                .setCallback(object : Callback<String> {
                    override fun onSuccess(t: String?, response: EasyVolleyResponse?) {
                        Log.d("code",  t.toString())
                    }

                    override fun onError(error: EasyVolleyError?) {
                        Log.e("code", " Error" + error!!.mStatusCode.toString())
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
    }
