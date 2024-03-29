package com.education.vidhyalayaaa.Fragments

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalayaaa.API.Data
import com.education.vidhyalayaaa.API.Notifactionpojo
import com.education.vidhyalayaaa.API.Services
import com.education.vidhyalayaaa.Activity.*
import com.education.vidhyalayaaa.Adapter.NotifactionAdapter
import com.education.vidhyalayaaa.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), Animation.AnimationListener {
    lateinit var progressDialog: ProgressDialog
    lateinit var img1: ImageView
    lateinit var mDrawerLayout: DrawerLayout
    lateinit var name: TextView
    lateinit var info: TextView
    lateinit var circleImageView: CircleImageView
    var holidaydata = ArrayList<Notifactionpojo>()
    lateinit var rv: RecyclerView
    var animFadein: Animation? = null

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return PlaceholderFragment.newInstance(
                position + 1
            )
        }

        override fun getCount(): Int {
            return 5
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.activity_home_fragment, container, false)

        img1 = view.findViewById(R.id.t_date)
        circleImageView = view.findViewById(R.id.circleImageView)
        name = view.findViewById(R.id.t_name)
        info = view.findViewById(R.id.t_info)
        holidaydata = ArrayList()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Please wait ...")
        progressDialog.setCancelable(false)
        img1.setOnClickListener { (context as Dashbord).open_navigation_drawable() }
        Picasso.with(context).load(Data.imageBaseUrl + getdata(Data.student_image))
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(circleImageView)
        name.text = getdata(Data.Student)

        if (getdata(Data.sectionname).toString().trim()
                .equals("0") || getdata(Data.sectionname).toString().trim().equals("")
        ) {
            info.text = "Class :-  " + getdata(Data.classe) + " | Section :- "
        } else {
            info.text = "Class :-  " + getdata(Data.classe) + " | Section :- " + getdata(Data.sectionname).toString()
                .trim()
        }

        val firstFragment = NotifactionFragment()
        var cv = view.findViewById<CardView>(R.id.cv)
        rv = view.findViewById<RecyclerView>(R.id.rv)

        var imageView3 = view.findViewById<ImageView>(R.id.imageView3)
            .setOnClickListener {
                imageView3.startAnimation(animFadein)
                cv.visibility = View.VISIBLE
                notifaction()
            }

        var i_closew = view.findViewById<ImageView>(R.id.i_closew)
            .setOnClickListener {
                cv.visibility = View.GONE
                holidaydata.clear()
            }


        animFadein = AnimationUtils.loadAnimation(context, R.anim.anim1)

        var c_moment = view.findViewById<LinearLayout>(R.id.c_moment)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    LoadMenu::class.java
                )
                intent.putExtra("name", "moment")
                startActivity(intent)
            }

        var c_homework = view.findViewById<LinearLayout>(R.id.c_homework)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    LoadMenu::class.java
                )

                intent.putExtra("name", "homework")
                startActivity(intent)
//            Toast.makeText(context,"Try Later",Toast.LENGTH_LONG).show()
            }
        var c_answersheet = view.findViewById<LinearLayout>(R.id.c_answersheet)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    LoadMenu::class.java
                )

                intent.putExtra("name", "answersheet")
                startActivity(intent)
//                Toast.makeText(context,"Try Later",Toast.LENGTH_LONG).show()
            }


        var c_leave = view.findViewById<LinearLayout>(R.id.c_leave)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    Leave::class.java
                )

                startActivity(intent)
            }

        var c_holiday = view.findViewById<LinearLayout>(R.id.c_holiday)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    LoadMenu::class.java
                )

                intent.putExtra("name", "holiday")
                startActivity(intent)
            }


        var c_feereport = view.findViewById<LinearLayout>(R.id.c_feereport)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    FeeActivity::class.java
                )
                startActivity(intent)
            }


        var c_attendence = view.findViewById<LinearLayout>(R.id.c_attendence)
            .setOnClickListener {
                var intent = Intent(
                    context,
                    Attendence::class.java
                )
                startActivity(intent)
            }


        var layout_help = view.findViewById<LinearLayout>(R.id.layout_help)
        layout_help.setOnClickListener {
            var intent = Intent(
                context,
                HelpPage::class.java
            )
            startActivity(intent)
        }

        var layoutMarkSheet = view.findViewById<LinearLayout>(R.id.layoutMarkSheet)
        layoutMarkSheet.setOnClickListener {
            var intent = Intent(
                context,
                MarkSheetActivity::class.java
            )
            startActivity(intent)
        }


        setdata(Data.login, "1")





        return view
    }

    class PlaceholderFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.activity_home_fragment, container, false)

            return rootView
        }

        companion object {
            /**
             * The fragment argument representing the section number for this
             * fragment.
             */
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment =
                    PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    fun getdata(key: String): String? {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "_")
        return value
    }

    fun notifaction() {
        holidaydata.clear()
        progressDialog.show()
        var preferences = Services()
        val loginResponseCall: Call<List<Notifactionpojo>> =
            preferences.getNotifactionservice()!!.getNotifaction(
                getdata(Data.school_id), getdata(
                    Data.Userid
                ), getdata(
                    Data.classid
                ), getdata(
                    Data.section
                )
            )

        loginResponseCall.enqueue(object : Callback<List<Notifactionpojo>> {
            override fun onFailure(call: Call<List<Notifactionpojo>>, t: Throwable) {
                if (progressDialog != null && progressDialog.isShowing) {
                    progressDialog.dismiss()
                }

                Toast.makeText(context, "Try Later", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<List<Notifactionpojo>>, response: Response<List<Notifactionpojo>>
            ) {


                if (response.isSuccessful) {
                    Log.e("@@homework", response.message())
                    Log.e("@@homework", response.body().toString())

                    if (progressDialog != null && progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    for (i in 0 until response.body()!!.size) {
                        mutableListOf<Notifactionpojo>()
                        holidaydata.addAll(listOf(response.body()!!.get(i)))
                    }
                    var adapter = NotifactionAdapter(
                        context!!.applicationContext,
                        holidaydata
                    )
                    rv.setBackgroundColor(R.color.app)
                    rv.layoutManager = GridLayoutManager(context, 1)
                    rv.isNestedScrollingEnabled = false
                    rv.setItemViewCacheSize(45)
                    rv.isDrawingCacheEnabled = true
                    rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                    rv.adapter = adapter

                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "Not getValid Data", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override
    fun onAnimationEnd(animation: Animation) {
        // Take any action after completing the animation

        // check for fade in animation
        if (animation === animFadein) {
        }
    }

    override
    fun onAnimationRepeat(animation: Animation?) {
    }

    override
    fun onAnimationStart(animation: Animation?) {
    }

    override fun onDestroy() {

        if (progressDialog != null && progressDialog.isShowing) {
            progressDialog.dismiss()
        }
        super.onDestroy()
    }

    fun setdata(key: String, value: String) {
        val sharedPreferences: SharedPreferences =
            context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
        editor.commit()
    }

    fun alertdilog() {
        val builder: AlertDialog.Builder? = context.let {
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
                activity!!.finish()
            }
        }
        val dialog: AlertDialog? = builder.create()

        dialog!!.show()
    }
/*    fun C_toast()
    {
        val myToast = Toast(context!!.applicationContext)
        myToast.duration = Toast.LENGTH_LONG
        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = temp1//setting the view of custom toast layout
        myToast.show()
    }*/
}