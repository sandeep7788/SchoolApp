package com.education.vidhyalayaaa.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalayaaa.API.*
import com.education.vidhyalayaaa.Adapter.AttendenceAdapter
import com.education.vidhyalayaaa.R
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Attendence : AppCompatActivity() {
    lateinit var pb: android.app.AlertDialog
    lateinit var rv: RecyclerView
    lateinit var t_totalday: TextView
    lateinit var t_persentday: TextView
    lateinit var i_backbutton: ImageView
    lateinit var imageView6: ImageView
    lateinit var t_teachername: TextView
    lateinit var txtAbsent: TextView
    lateinit var txtLeave: TextView
    lateinit var txtMonth: TextView
    var userdata = ArrayList<AttendanceList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendence)
        t_totalday = findViewById(R.id.txtTotalMeeting)
        t_persentday = findViewById(R.id.txtPresent)
        i_backbutton = findViewById(R.id.i_backbutton)
        imageView6 = findViewById(R.id.imageView6)
        t_teachername = findViewById(R.id.txtTeacher)
        txtAbsent = findViewById(R.id.txtAbsent)
        txtLeave = findViewById(R.id.txtLeave)
        txtMonth = findViewById(R.id.txtMonth)
        rv = findViewById(R.id.rv)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        pb = SpotsDialog.Builder().setContext(this@Attendence).build()
        pb.setTitle("Connecting to Server")
        pb.setMessage("Please Wait")
        pb.setCancelable(false)
        userdata = ArrayList()
        Attendencedata1()

        var MyReceiver: BroadcastReceiver? = null
        MyReceiver = com.education.vidhyalayaaa.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))


        i_backbutton.setOnClickListener { finish() }
        imageView6.setOnClickListener {
            Attendencedata()
            Attendencedata1()
        }

        rv.getBackground().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.DARKEN)
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value
    }
//holiday date
//    attence show error
//    null no data found
//    fees table

    fun Attendencedata() {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
        val currentDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

        Toast.makeText(
            this,
            "Today's Date: $currentYear$currentMonth$currentDay",
            Toast.LENGTH_SHORT
        ).show()
        userdata.clear()
        pb.show()


        var preferences = Services()
        val loginResponseCall: Call<Attendentapi> =
            preferences.getAttendenceList()!!.get_atendenceList(
                getdata(Data.school_id)!!.trim().toInt(),
                getdata(Data.id)!!.trim().toInt(),
                (currentMonth).toString(),
                (currentYear).toString()
            )
        loginResponseCall.enqueue(object : Callback<Attendentapi> {
            override fun onFailure(call: Call<Attendentapi>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(this@Attendence, "Failure" + t.message, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<Attendentapi>,
                response: Response<Attendentapi>
            ) {

                pb.dismiss()
                userdata.clear()

                if (response.isSuccessful) {


                    if (response != null) {
                        for (i in 0 until response.body()!!.attendanceList!!.size) {
                            mutableListOf<Holadaypojo>()
                            userdata.addAll(listOf(response.body()!!.attendanceList.get(i)))
                        }
                        userdata.reverse()


                        var adapter = AttendenceAdapter(
                            this@Attendence.applicationContext,
                            userdata
                        )
                        rv.setBackgroundColor(getColor(R.color.app))
                        rv.layoutManager = GridLayoutManager(this@Attendence, 1)
                        rv.isNestedScrollingEnabled = false
                        rv.setItemViewCacheSize(45)
                        rv.isDrawingCacheEnabled = true
                        rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                        rv.adapter = adapter
                        adapter.notifyDataSetChanged()

                    }

                } else {
                    Toast.makeText(this@Attendence, "Try Again or Sync", Toast.LENGTH_LONG).show()
                }
            }

        })
    }

    fun Attendencedata1() {
        val calendar: Calendar = Calendar.getInstance(TimeZone.getDefault())

        val currentYear: Int = calendar.get(Calendar.YEAR)
        val currentMonth: Int = calendar.get(Calendar.MONTH) + 1
        val currentDay: Int = calendar.get(Calendar.DAY_OF_MONTH)

        Toast.makeText(
            this,
            "Today's Date: $currentYear    $currentMonth    $currentDay",
            Toast.LENGTH_SHORT
        ).show()

        pb.show()
        var preferences = Services()
        val loginResponseCall: Call<Attendentapi> =
            preferences.getAttendenceList()!!.get_atendenceList(
                getdata(Data.school_id)!!.trim().toInt(),
                getdata(Data.id)!!.trim().toInt(),
                (currentMonth).toString(),
                (currentYear).toString()
            )

        loginResponseCall.enqueue(object : Callback<Attendentapi> {
            override fun onFailure(call: Call<Attendentapi>, t: Throwable) {
                pb.dismiss()

                Toast.makeText(
                    this@Attendence,
                    "Failure " + t.message + "Sync Again",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<Attendentapi>,
                response: Response<Attendentapi>
            ) {
                pb.dismiss()
                if (response.isSuccessful) {

                    t_totalday.text = response.body()!!.totalDays + " "
                    t_persentday.text = response.body()!!.totalAttendance + " "
                    t_teachername.text = response.body()!!.teacherName
                    txtLeave.text = "" + response.body()!!.total_leave
                    txtAbsent.text = "" + response.body()!!.total_absent

                    Attendencedata()


                } else {
                    Toast.makeText(this@Attendence, "Try Again or Sync", Toast.LENGTH_LONG).show()
                }

                val month: String =
                    calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                txtMonth.text = month
            }

        })
    }

    override fun onDestroy() {
        if (pb != null && pb.isShowing) {
            pb.dismiss()
        }
        this@Attendence.onVisibleBehindCanceled()
        super.onDestroy()
    }
}