package com.education.vidhyalayaaa.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.education.vidhyalayaaa.API.Data
import com.education.vidhyalayaaa.API.Services
import com.education.vidhyalayaaa.API.UserLogin
import com.education.vidhyalayaaa.API.User_List
import com.education.vidhyalayaaa.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Splach_screen : AppCompatActivity() {

    var userdata=ArrayList<User_List>()
    lateinit var pb: android.app.AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)
        pb= SpotsDialog.Builder().setContext(this@Splach_screen).build()
        pb.setMessage("Please Wait")
        userdata= ArrayList()

        Handler().postDelayed({

            if(getdata(Data.login).equals("1")) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(
                    OnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.w("@@", "Fetching FCM registration token failed", task.exception)
                            login1(task.result.toString())

                            return@OnCompleteListener
                        } else {
                            login1(" ")
                        }
                    })
            } else {
                startActivity(Intent(this@Splach_screen, MainActivity::class.java))
                finish()
            }

        }, 2000)



    }

    fun login1(token:String)
    {
        Log.e("@@", "login1: "+token)
        var preferences= Services()
        val loginResponseCall: Call<List<UserLogin>> = preferences.getServices()!!.getProducts(getdata(Data.number),getdata(Data.password),token)

        loginResponseCall.enqueue(object : Callback<List<UserLogin>>
        {
            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                pb!!.dismiss()
                startActivity(Intent(this@Splach_screen, MainActivity::class.java))
                finish()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<UserLogin>>,
                response: Response<List<UserLogin>>
            ) {
                if (response.isSuccessful) {

                    pb.dismiss()
                    startActivity(Intent(this@Splach_screen, MainActivity::class.java))
                    finish()
                }
            }
        })
    }
    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value
    }

    fun setdata(id:String,Userid:String,classid:String,classe:String,section:String,Student:String,student_image:String,
                school_id:String,school_name:String,staffid:String,staffuserid:String,Classteacher:String,FacultyPhone:String)
    {
        val sharedPreferences: SharedPreferences = getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("id",id)
        editor.putString("Userid",Userid)
        editor.putString("classid",classid)
        editor.putString("classe",classe)
        editor.putString("section",section)
        editor.putString("Student",Student)
        editor.putString("student_image",student_image)
        editor.putString("school_id",school_id)
        editor.putString("school_name",school_name)
        editor.putString("staffid",staffid)
        editor.putString("staffuserid",staffuserid)
        editor.putString("Classteacher",Classteacher)
        editor.putString("FacultyPhone",FacultyPhone)
        editor.apply()
        editor.commit()
    }override fun onDestroy() {

        this!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
}
