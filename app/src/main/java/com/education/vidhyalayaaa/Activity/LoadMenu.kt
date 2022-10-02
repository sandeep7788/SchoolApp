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
import com.education.vidhyalayaaa.API.Holadaypojo
import com.education.vidhyalayaaa.API.MomentApi
import com.education.vidhyalayaaa.API.Services
import com.education.vidhyalayaaa.Adapter.AnswereSheetAdapter
import com.education.vidhyalayaaa.Adapter.DashbordMenuAdapter
import com.education.vidhyalayaaa.Adapter.HolidayAdapter
import com.education.vidhyalayaaa.Adapter.HomeworkAdapter
import com.education.vidhyalayaaa.R
import com.education.vidhyalayaaa.model.AnswerSheetModel
import com.education.vidhyalayaaa.model.HomeWorkModel
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadMenu : AppCompatActivity() {

    lateinit var progressDialog: android.app.AlertDialog
    lateinit var img1: ImageView
    lateinit var name: TextView
    lateinit var info: TextView
    lateinit var txt_title: TextView
    lateinit var rv: RecyclerView
    lateinit var back_button: ImageView
    lateinit var sync: ImageView
    var menudata = ArrayList<MomentApi>()
    var holidaydata = ArrayList<Holadaypojo>()
    var homeWorkList = ArrayList<HomeWorkModel>()
    var answereSheetList = ArrayList<AnswerSheetModel>()
    lateinit var imageView5: ImageView
    var data = "0"
    lateinit var imageView8: ImageView
    var TAG = ">>LoadMenu"
    var errorMsg = "something went wrong please try again."

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_load_menu)
        rv = findViewById(R.id.rv)
        back_button = findViewById(R.id.imageView4)
        sync = findViewById(R.id.imageView6)
        imageView5 = findViewById(R.id.imageView5)
        imageView8 = findViewById(R.id.imageView8)
        txt_title = findViewById(R.id.txt_title)
        menudata = ArrayList()
        holidaydata = ArrayList()
        homeWorkList = ArrayList()
        answereSheetList = ArrayList()
        progressDialog = SpotsDialog.Builder().setContext(this@LoadMenu).build()
        progressDialog.setTitle("Connecting to Server")
        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)

        progressDialog.show()
        var intent = intent
        data = intent.getStringExtra("name").toString()
        when (data) {
            "moment" -> moment()
            "homework" -> homework()
            "answersheet" -> answeresheet()
            "holiday" -> holiday()
            else
            -> Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
        }

        var MyReceiver: BroadcastReceiver? = null
        MyReceiver = com.education.vidhyalayaaa.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))



        back_button.setOnClickListener {
            finish()
        }


        sync.setOnClickListener {

            progressDialog.show()
            menudata.clear()
            holidaydata.clear()
            homeWorkList.clear()
            answereSheetList.clear()

            when (data) {

                "moment" -> moment()
                "homework" -> homework()
                "answersheet" -> answeresheet()
                "holiday" -> holiday()
                else
                -> Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
            }
        }
    }

    fun moment() {
        txt_title.text = "Moment"
        var preferences = Services()
        val loginResponseCall: Call<List<MomentApi>> =
            preferences.get_dashbord_menu()!!.getmenu(getdata(Data.school_id))
        loginResponseCall.enqueue(object : Callback<List<MomentApi>> {

            override fun onFailure(call: Call<List<MomentApi>>, t: Throwable) {
                Toast.makeText(
                    this@LoadMenu,
                    errorMsg,
                    Toast.LENGTH_LONG
                ).show()
                imageView8.visibility = View.VISIBLE
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()

                }
                Log.d(TAG, "onFailure: 3" + t.message)
            }

            override fun onResponse(
                call: Call<List<MomentApi>>,
                response: Response<List<MomentApi>>
            ) {
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                if (response.isSuccessful) {

                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG)
                            .show()
                        imageView8.visibility = View.VISIBLE
                    } else {
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<MomentApi>()
                            menudata.addAll(listOf(response.body()!!.get(i)))
                        }
                        var adapter = DashbordMenuAdapter(
                            this@LoadMenu.applicationContext,
                            menudata
                        )
                        rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                        rv.isNestedScrollingEnabled = false
                        rv.setItemViewCacheSize(45)
                        rv.isDrawingCacheEnabled = true
                        rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        rv.adapter = adapter

                    }
                } else {
                    Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
                    imageView8.visibility = View.VISIBLE
                }
            }
        })
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "_")
        return value
    }

    fun homework() {
        txt_title.text = "Homework"
        var preferences = Services()
        val loginResponseCall: Call<List<HomeWorkModel>> =
            preferences.gethomeworkservices()!!.gethomework(
                getdata(Data.school_id).toString().trim().toInt(), getdata(
                    Data.id
                ).toString().trim().toInt(), getdata(
                    Data.classid
                ).toString().trim().toInt(), getdata(
                    Data.section
                ).toString().trim().toInt()
            )


        loginResponseCall.enqueue(object : Callback<List<HomeWorkModel>> {
            override fun onFailure(call: Call<List<HomeWorkModel>>, t: Throwable) {

                Log.d(TAG, "onFailure: " + t.message)
                Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
                imageView8.visibility = View.VISIBLE
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onResponse(
                call: Call<List<HomeWorkModel>>, response: Response<List<HomeWorkModel>>
            ) {

                Log.e(TAG, "onResponse: " + response.body()?.size)
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }

                if ((response.body()).toString().trim().equals("[]")) {
                    Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG)
                        .show()
                    imageView8.visibility = View.VISIBLE
                } else {
                    for (i in 0 until response.body()!!.size) {
                        mutableListOf<HomeWorkModel>()
                        homeWorkList.addAll(listOf(response.body()!!.get(i)))
                    }
                    imageView8.visibility = View.GONE
                    var adapter = HomeworkAdapter(
                        this@LoadMenu.applicationContext,
                        homeWorkList
                    )
                    rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                    rv.isNestedScrollingEnabled = false
                    rv.setItemViewCacheSize(45)
                    rv.isDrawingCacheEnabled = true
                    rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    rv.adapter = adapter

                }
            }
        })
    }

    fun holiday() {
        txt_title.text = "Holiday"
        var preferences = Services()
        val loginResponseCall: Call<List<Holadaypojo>> =
            //preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
            preferences.getholidayservices()!!.getholiday(getdata(Data.school_id))
        loginResponseCall.enqueue(object : Callback<List<Holadaypojo>> {
            override fun onFailure(call: Call<List<Holadaypojo>>, t: Throwable) {

                imageView8.visibility = View.VISIBLE
                Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
                Log.d(TAG, "onFailure: 1" + t.message)
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onResponse(
                call: Call<List<Holadaypojo>>, response: Response<List<Holadaypojo>>
            ) {
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                if (response.isSuccessful) {

                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG)
                            .show()
                        imageView8.visibility = View.VISIBLE
                    } else if (response != null) {
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Holadaypojo>()
                            holidaydata.addAll(listOf(response.body()!!.get(i)))
                        }
                        imageView8.visibility = View.GONE
                        var adapter = HolidayAdapter(
                            this@LoadMenu.applicationContext,
                            holidaydata
                        )
//                        rv.setBackgroundColor(resources.getColor(R.color.app))
                        rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                        rv.isNestedScrollingEnabled = false
                        rv.setItemViewCacheSize(45)
                        rv.isDrawingCacheEnabled = true
                        rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        rv.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
                    imageView8.visibility = View.VISIBLE
                }
            }
        })
    }

    fun answeresheet() {
        txt_title.text = "Answer Sheet"
        var preferences = Services()
        val loginResponseCall: Call<List<AnswerSheetModel>> =
            //preferences.getUserliost()!!.getUserlist(e1.text.toString(),section)
            preferences.getanswersheetservices()!!.getanswersheet(
                getdata(Data.school_id), getdata(
                    Data.id
                )
            )
        loginResponseCall.enqueue(object : Callback<List<AnswerSheetModel>> {
            override fun onFailure(call: Call<List<AnswerSheetModel>>, t: Throwable) {

                Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
                imageView8.visibility = View.VISIBLE
                Log.d(TAG, "onFailure: 2" + t.message)
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }

            override fun onResponse(
                call: Call<List<AnswerSheetModel>>, response: Response<List<AnswerSheetModel>>
            ) {
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                if (response.isSuccessful) {
                    if ((response.body()).toString().trim().equals("[]")) {
                        Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG)
                            .show()
                        imageView8.visibility = View.VISIBLE
                    } else {
                        for (i in 0 until response.body()!!.size) {
                            mutableListOf<Holadaypojo>()
                            answereSheetList.addAll(listOf(response.body()!!.get(i)))
                        }
                        imageView8.visibility = View.GONE
                        var adapter = AnswereSheetAdapter(
                            this@LoadMenu.applicationContext,
                            answereSheetList
                        )
                        rv.setBackgroundColor(resources.getColor(R.color.app))
                        rv.layoutManager = GridLayoutManager(this@LoadMenu, 1)
                        rv.isNestedScrollingEnabled = false
                        rv.setItemViewCacheSize(45)
                        rv.isDrawingCacheEnabled = true
                        rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        rv.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }
                } else {
                    Toast.makeText(this@LoadMenu, errorMsg, Toast.LENGTH_LONG).show()
                    imageView8.visibility = View.VISIBLE
                }
            }

        })
    }

    override fun onDestroy() {

        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        this.onVisibleBehindCanceled()
        super.onDestroy()
    }

    override fun onBackPressed() {
        finish()
    }
}