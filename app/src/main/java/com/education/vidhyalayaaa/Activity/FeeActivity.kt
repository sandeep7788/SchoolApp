package com.education.vidhyalayaaa.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalayaaa.API.Data
import com.education.vidhyalayaaa.API.Feesdet
import com.education.vidhyalayaaa.API.Services
import com.education.vidhyalayaaa.Adapter.FeeAdapter
import com.education.vidhyalayaaa.R
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class FeeActivity : AppCompatActivity() {
    lateinit var progressDialog: android.app.AlertDialog
    lateinit var img1: ImageView
    lateinit var name: TextView
    lateinit var info: TextView
    lateinit var rv: RecyclerView
    lateinit var back_button: ImageView
    lateinit var sync: ImageView
    var menudata=ArrayList<Feesdet>()
    lateinit var imageView5: ImageView
    var data="0"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fee)
        rv=findViewById(R.id.rv)
        back_button=findViewById(R.id.imageView4)
        sync=findViewById(R.id.imageView6)
        imageView5=findViewById(R.id.imageView5)
        menudata= ArrayList()
        progressDialog= SpotsDialog.Builder().setContext(this@FeeActivity).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)

        var MyReceiver: BroadcastReceiver?= null;
        MyReceiver = com.education.vidhyalayaaa.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        Log.d(">>", "onCreate: ")
        moment()
        back_button.setOnClickListener {
        finish()
        }

        sync.setOnClickListener { moment() }

    }
    fun moment()
    {
        menudata.clear()
        progressDialog.show()
        var preferences= Services()
        val loginResponseCall: Call<List<Feesdet>> =
            preferences.getfee_details()!!.getfeedetails(getdata(Data.school_id),getdata(Data.id))
        Log.d(">>", "moment: "+getdata(Data.school_id)+" _ "+getdata(Data.id))
        loginResponseCall.enqueue(object : Callback<List<Feesdet>>
        {
            override fun onFailure(call: Call<List<Feesdet>>, t: Throwable) {
                Toast.makeText(this@FeeActivity, "Check Your Connection Or Try Later", Toast.LENGTH_LONG).show()

                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
            }

            override fun onResponse(call: Call<List<Feesdet>>, response: Response<List<Feesdet>>) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
                if(response.isSuccessful) {


                    try {

                    for (i in 0 until response.body()!!.size) {
                        Log.e(">>", response!!.body()?.get(i)?.amt.toString())
                        mutableListOf<Feesdet>()
                        menudata.addAll(listOf(response.body()!!.get(i)))
                    }
                    var adapter = FeeAdapter(this@FeeActivity!!.applicationContext,menudata)

                    rv.layoutManager= GridLayoutManager(this@FeeActivity,1)
                    rv.setNestedScrollingEnabled(false);
                    rv.setItemViewCacheSize(45);
                    rv.setDrawingCacheEnabled(true);
                    rv.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                    rv.adapter = adapter
                        adapter.notifyDataSetChanged()
                    } catch (e:Exception) {  Toast.makeText(this@FeeActivity,"Don't Have Any Data", Toast.LENGTH_LONG).show() }
                }
                else
                {
                    Toast.makeText(this@FeeActivity,"Don't Have Any Data", Toast.LENGTH_LONG).show()
                }
            }


        })
    }

    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = this!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"_")
        return value
    }override fun onDestroy() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
        this!!.onVisibleBehindCanceled()
        super.onDestroy()
    }
}