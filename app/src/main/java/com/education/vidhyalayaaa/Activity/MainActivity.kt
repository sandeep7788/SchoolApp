package com.education.vidhyalayaaa.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalayaaa.API.Data
import com.education.vidhyalayaaa.API.Services
import com.education.vidhyalayaaa.API.UserLogin
import com.education.vidhyalayaaa.API.User_List
import com.education.vidhyalayaaa.Adapter.UserAdapter
import com.education.vidhyalayaaa.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var e1: EditText
    lateinit var e2: EditText
    lateinit var pb: android.app.AlertDialog
    lateinit var sv: ScrollView
    lateinit var rv: RecyclerView
    var userdata = ArrayList<User_List>()
    var errorMsg = "something went wrong please try again."

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        e1 = findViewById(R.id.e1)
        e2 = findViewById(R.id.e2)
        sv = findViewById(R.id.sv)
        rv = findViewById(R.id.rv)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        pb = SpotsDialog.Builder().setContext(this@MainActivity).build()
        pb.setTitle("Connecting to Server")
        pb.setMessage("Please Wait")
        userdata = ArrayList()

        var MyReceiver: BroadcastReceiver? = null
        MyReceiver = com.education.vidhyalayaaa.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        var b_login = findViewById<Button>(R.id.b_login)
        b_login.setOnClickListener {
            sv.visibility = View.VISIBLE
            var cl = findViewById<ConstraintLayout>(R.id.cl)
            var l1 = findViewById<LinearLayout>(R.id.l1)
            cl.setBackgroundColor(R.color.tw)
            l1.visibility = View.INVISIBLE
        }

        if (getdata(Data.login).equals("1")) {
            Log.e("@@", "number " + getdata(Data.number) + "section " + getdata(Data.schoolSession))
            load_user(getdata(Data.number), getdata(Data.schoolSession))
        }


        var b1 = findViewById<Button>(R.id.button)
            .setOnClickListener {

                if (e1.text.isEmpty()) {
                    e1.error = "Invalid"
                } else if (e2.text.isEmpty()) {
                    e2.error = "Invalid Password"
                } else if (!e1.text.isEmpty() && !e2.text.isEmpty()) {
                    pb.show()
//                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
                    hideTheKeyboard(this@MainActivity, e2)

                    FirebaseMessaging.getInstance().token.addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (!task.isSuccessful) {
                                Log.w(
                                    "@@",
                                    "Fetching FCM registration token failed",
                                    task.exception
                                )
                                login1(" ")
                                return@OnCompleteListener
                            } else {
                                login1(task.result.toString())
                            }
                        })
                }
            }
    }

    fun hideTheKeyboard(context: Context, editText: EditText) {
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            editText.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    fun login1(token: String) {
        var preferences = Services()
        val loginResponseCall: Call<List<UserLogin>> =
            preferences.getServices()!!.getProducts(e1.text.toString(), e2.text.toString(), token)

        loginResponseCall.enqueue(object : Callback<List<UserLogin>> {
            override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(
                    this@MainActivity,
                    "Username or Password incorrect",
                    Toast.LENGTH_LONG
                ).show()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<UserLogin>>,
                response: Response<List<UserLogin>>
            ) {
                if (response.isSuccessful) {

                    pb.dismiss()
                    setdata()
                    setdata(Data.number, e1.text.toString())
                    setdata(Data.password, e2.text.toString())
                    Toast.makeText(this@MainActivity, "Login Successfully", Toast.LENGTH_LONG)
                        .show()

                    if (response.body()?.size!!.toInt() > 0) {

                        setdata(
                            Data.schoolSession,
                            (response.body()!!.get(0).schoolSession + " ").trim()
                        )
                        Log.e("@@section", response.body()!!.get(0).schoolSession)

                        load_user((response.body()!!.get(0).schoolSession + " ").trim())
                    } else {
                        Toast.makeText(this@MainActivity, "No data found!", Toast.LENGTH_LONG)
                            .show()

                    }
                }
            }
        })
    }

    fun load_user(schoolSession: String) {

        userdata.clear()
        var preferences = Services()
        val loginResponseCall: Call<List<User_List>> =
            preferences.getUserliost()!!.getUserlist(e1.text.toString(), schoolSession)
        loginResponseCall.enqueue(object : Callback<List<User_List>> {

            override fun onFailure(call: Call<List<User_List>>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_SHORT).show()
                sv.visibility = View.VISIBLE
                var cl = findViewById<ConstraintLayout>(R.id.cl)
                var l1 = findViewById<LinearLayout>(R.id.l1)
                l1.visibility = View.INVISIBLE
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<User_List>>,
                response: Response<List<User_List>>
            ) {
                pb.dismiss()
                try {


                    if (response.isSuccessful) {
                        sv.visibility = View.INVISIBLE
                        var cl = findViewById<ConstraintLayout>(R.id.cl)
                        var l1 = findViewById<LinearLayout>(R.id.l1)
                        cl.setBackgroundColor(R.color.tb)
                        l1.visibility = View.VISIBLE

                        for (i in 0 until response.body()!!.size) {

                            setdata("id", response.body()!!.get(i).id)
                            setdata("studentImage", response.body()!!.get(i).studentImage)
                            setdata("student", response.body()!!.get(i).student)

                            mutableListOf<User_List>()
                            userdata.addAll(listOf(response.body()!!.get(i)))
                        }
                        setdata(Data.number, e1.text.toString())
                        var adapter = UserAdapter(
                            this@MainActivity,
                            userdata
                        )
                        rv.layoutManager = LinearLayoutManager(this@MainActivity)
                        rv.adapter = adapter
                    } else {
                        Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, errorMsg, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun setdata() {

        val sharedPreferences: SharedPreferences = getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        editor.putString("id", "id")
        editor.putString("Userid", " ....")
        editor.putString("classid", " ....")
        editor.putString("classe", " ....")
        editor.putString("Student", " ....")
        editor.putString("student_image", " ....")
        editor.putString("school_id", " ....")
        editor.putString("school_name", " ....")
        editor.putString("staffid", " ....")
        editor.putString("staffuserid", " ....")
        editor.putString("Classteacher", " ....")
        editor.putString("FacultyPhone", " ....")
        editor.putString(Data.student_image, " ....")
        editor.apply()
        editor.commit()
    }

    @SuppressLint("ResourceAsColor")
    fun load_user(number: String, section: String) {
        var cl = findViewById<ConstraintLayout>(R.id.cl)
        var l1 = findViewById<LinearLayout>(R.id.l1)
        cl.setBackgroundColor(R.color.tb)
        pb.show()
        userdata.clear()
        Toast.makeText(this@MainActivity, "Wait", Toast.LENGTH_LONG).show()

        var preferences = Services()
        val loginResponseCall: Call<List<User_List>> =
            preferences.getUserliost()!!.getUserlist(number.trim(), section.trim())
        loginResponseCall.enqueue(object : Callback<List<User_List>> {

            override fun onFailure(call: Call<List<User_List>>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(this@MainActivity, "Try Again", Toast.LENGTH_SHORT).show()
                sv.visibility = View.VISIBLE
                l1.visibility = View.INVISIBLE
            }

            override fun onResponse(
                call: Call<List<User_List>>,
                response: Response<List<User_List>>
            ) {
                pb.dismiss()
                userdata.clear()

                if (response.isSuccessful) {
                    sv.visibility = View.INVISIBLE
                    l1.visibility = View.VISIBLE
                    setdata(Data.schoolSession, section)

                    //if(response.body()==null) {

                    for (i in 0 until response.body()!!.size) {
                        mutableListOf<User_List>()
                        userdata.addAll(listOf(response.body()!!.get(i)))
                    }
                    var adapter = UserAdapter(
                        this@MainActivity,
                        userdata
                    )
                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                    rv.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "not get valid data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    fun alertdilog() {
        val builder: AlertDialog.Builder? = this.let {
            AlertDialog.Builder(it)
        }


        builder!!.setMessage("Are you sure you want to Exit ?")
            .setTitle("Exit")

        builder.apply {
            setPositiveButton("No") { adialog, id ->
                val selectedId = id
                adialog.dismiss()
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
        alertdilog()
    }


    fun setdata(key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            this.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun getdata(key: String): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "d_null")
        return value!!
    }

    override fun onDestroy() {
        if (pb != null && pb.isShowing) {
            pb.dismiss()
        }
        this.onVisibleBehindCanceled()
        super.onDestroy()
    }
}