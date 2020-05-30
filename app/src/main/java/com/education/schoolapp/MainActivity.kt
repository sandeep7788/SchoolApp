package com.education.schoolapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.schoolapp.API.UserLogin
import com.education.schoolapp.API.User_List
import com.education.schoolapp.Adapter.UserAdapter
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{
    lateinit var e1:EditText
    lateinit var e2:EditText
    lateinit var pb: android.app.AlertDialog
    lateinit var sv:ScrollView
    lateinit var rv:RecyclerView
    var userdata=ArrayList<User_List>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        e1=findViewById(R.id.e1)
        e2=findViewById(R.id.e2)
        sv=findViewById(R.id.sv)
        rv=findViewById(R.id.rv)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        pb=SpotsDialog.Builder().setContext(this@MainActivity).build()
        pb!!.setTitle("Please Wait")
        userdata= ArrayList()

        //startActivity(Intent(this, Dashbord::class.java))
        var textView=findViewById<TextView>(R.id.textView)
            .setOnClickListener {  startActivity(Intent(this,
                Dashbord::class.java)) }

        val sharedPreferences: SharedPreferences = this.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("classid","1234")
        editor.putString("class","1234")
        editor.putString("school_name","1234")
//        editor.apply()
        editor.commit()


        var b1=findViewById<Button>(R.id.button)
            .setOnClickListener {

                if(e1.text.isEmpty())
                {
                    e1.setError("Invalid")
                }
                else if(e2.text.isEmpty())
                {
                    e2.setError("Invalid Password")
                }
                else if (!e1.text.isEmpty()&&!e2.text.isEmpty())
                {
                    pb!!.show()
                    login1()
                }
            }
    }

    fun login1()
    {
        var preferences= Services()
        val loginResponseCall: Call<List<UserLogin>> =
            preferences.getServices()!!.getProducts(e1.text.toString(),e2.text.toString(),"test123")

        loginResponseCall.enqueue(object : Callback<List<UserLogin>>
        {
            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                pb!!.dismiss()
                Toast.makeText(this@MainActivity,"Username or Password incorrect",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<UserLogin>>,
                response: Response<List<UserLogin>>
            ) {
                response.isSuccessful

                /*if(response.body()!!.get(0).error != null)
                {
                    Toast.makeText(this@MainActivity, ""+ response.body()!!.get(0).error, Toast.LENGTH_LONG)
                        .show()
                }
                else {*/



                    pb!!.dismiss()
                    Toast.makeText(this@MainActivity, "Login Successfully", Toast.LENGTH_LONG).show()
                    sv.visibility = View.INVISIBLE
                    var cl = findViewById<ConstraintLayout>(R.id.cl)
                    var l1 = findViewById<LinearLayout>(R.id.l1)
                    cl.setBackgroundColor(R.color.tb)
                    l1.visibility = View.VISIBLE
                    load_user(response.body()!!.get(0).schoolSession)
                    /*for (i in 0 until response.body()!!.size)
                {
                 Log.e("@@login", response.body()!!.get(i).id)
                    Log.e("@@login", response.body()!!.get(i).schoolSession)
                    Log.e("@@login", response.body()!!.get(i).login)

                    *//*mutableListOf<UserLogin>()
                    userdata?.addAll(listOf(response.body()!!.get(i)))*//*
                }*/
                }
//            }
        })
    }
//    98287753333 1234 scid= 32

    fun load_user(section:String)
    {
        var preferences= Services()
        val loginResponseCall: Call<List<User_List>> =
            preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
        loginResponseCall.enqueue(object : Callback<List<User_List>>
        {

            override fun onFailure(call: Call<List<User_List>>, t: Throwable) {
                Log.e("@@userlist",t.message)
            }

            override fun onResponse(
                call: Call<List<User_List>>,
                response: Response<List<User_List>>
            ) {
                setdata(Data.schoolSession,section)
                setdata(Data.number,e1.text.toString())
                //if(response.body()==null) {
                    Log.e("@@userlist", response.body().toString())
                    for (i in 0 until response.body()!!.size) {
                        Log.e("@@userlist", response.body()!!.get(i).id)
                        Log.e("@@userlist", response.body()!!.get(i).studentImage)
                        Log.e("@@userlist", response.body()!!.get(i).student)
                        mutableListOf<User_List>()
                        userdata.addAll(listOf(response.body()!!.get(i)))
                    }
                    var adapter = UserAdapter(
                        this@MainActivity,
                        userdata
                    )
                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv.adapter = adapter
                //}
                /*else
                {
                    Toast.makeText(this@MainActivity,"not get valid data",Toast.LENGTH_SHORT).show()
                }*/
            }
        })
    }
    /*private fun showDialog(title: String) {
        val dialog = Dialog(this@MainActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.cutsom_layout)
        val body = dialog.findViewById(R.id.body) as TextView
        body.text = title
        val yesBtn = dialog.findViewById(R.id.yesBtn) as Button
        val noBtn = dialog.findViewById(R.id.noBtn) as TextView
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()

    }*/
    fun alertdilog()
    {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }


        builder!!.setMessage("Are you sure you want to Exit ?")
            .setTitle("Exit")

        builder.apply {
            setPositiveButton("No") { dialog, id ->
                val selectedId = id
                dialog.dismiss()
            }
            setNegativeButton("Yes") { dialog, id ->
                val selectedId = id
                finish()
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        alertdilog()
    }
    fun setdata(key:String,value:String)
    {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString(key,value)
        editor.apply()
        editor.commit()
    }
}