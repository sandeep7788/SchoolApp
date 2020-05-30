package com.education.schoolapp

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.schoolapp.API.MomentApi
import com.education.schoolapp.Adapter.DashbordMenuAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadMenu : AppCompatActivity() {

    lateinit var progressDialog: ProgressDialog
    lateinit var img1: ImageView
    lateinit var name: TextView
    lateinit var info: TextView
    lateinit var rv: RecyclerView
    lateinit var back_button:ImageView
    lateinit var sync:ImageView
    var menudata=ArrayList<MomentApi>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_menu)
        rv=findViewById(R.id.rv)
        back_button=findViewById(R.id.imageView4)
        sync=findViewById(R.id.imageView6)
        /*img1=findViewById(R.id.img1)
        name=findViewById(R.id.t_name)
        info=findViewById(R.id.t_info)*/
        menudata= ArrayList()
        progressDialog = ProgressDialog(this@LoadMenu)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        Moment()
        back_button.setOnClickListener { finish() }
        sync.setOnClickListener { Moment() }

    }
    fun Moment()
    {
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<MomentApi>> =
            //preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
            preferences.get_dashbord_menu()!!.getmenu("28")
        loginResponseCall.enqueue(object : Callback<List<MomentApi>>
        {

            override fun onFailure(call: Call<List<MomentApi>>, t: Throwable) {
                Log.e("@@userlist",t.message)
                Toast.makeText(this@LoadMenu, "Check Your Connection Or Try Later", Toast.LENGTH_LONG).show()
                progressDialog.dismiss()
            }

            override fun onResponse(
                call: Call<List<MomentApi>>,
                response: Response<List<MomentApi>>
            ) {
                progressDialog.dismiss()
                Log.e("@@userlist", response.body().toString())
                //if(response.body()==null) {
                Log.e("@@userlist", response.body().toString())
                for (i in 0 until response.body()!!.size) {
                    Log.e("@@userlist", response.body()!!.get(i).id)
                    mutableListOf<MomentApi>()
                    menudata.addAll(listOf(response.body()!!.get(i)))
                }
                var adapter = DashbordMenuAdapter(
                    this@LoadMenu!!.applicationContext,
                    menudata
                )
                rv.layoutManager= GridLayoutManager(this@LoadMenu,2)
                rv.setNestedScrollingEnabled(false);
                rv.setItemViewCacheSize(45);
                rv.setDrawingCacheEnabled(true);
                rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                rv.adapter = adapter
                //}
                /*else
                {
                    Toast.makeText(this@MainActivity,"not get valid data",Toast.LENGTH_SHORT).show()
                }*/
            }
        })
    }
    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = this!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"defoult_value")
        return value
    }
}
