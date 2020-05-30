package com.education.schoolapp.Fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.schoolapp.*
import com.education.schoolapp.API.MomentApi
import com.education.schoolapp.Adapter.DashbordMenuAdapter
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    lateinit var progressDialog: ProgressDialog
    lateinit var img1:ImageView
    lateinit var mDrawerLayout:DrawerLayout
    lateinit var name:TextView
    lateinit var info:TextView
    lateinit var circleImageView:CircleImageView
    var userdata=ArrayList<MomentApi>()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        val view = inflater!!.inflate(R.layout.activity_home_fragment, container, false)

        img1=view.findViewById(R.id.img1)
        circleImageView=view.findViewById(R.id.circleImageView)
        name=view.findViewById(R.id.t_name)
        info=view.findViewById(R.id.t_info)
        userdata= ArrayList()
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("loading")
        progressDialog.setCancelable(false)
        img1.setOnClickListener { (context as Dashbord).open_navigation_drawable() }
        Picasso.with(context).load("http://skoolstarr.com/sspanel/"+getdata(Data.student_image))
            .error(R.drawable.ic_account_circle_black_24dp)
            .into(circleImageView)
        name.setText(getdata(Data.Student))
        info.setText("Class:- "+getdata(Data.classe)+" | Section:-"+getdata(Data.section))
        Log.e("@@h",getdata(Data.Student)+"data"+Data.Student)
        var c_moment=view.findViewById<CardView>(R.id.c_moment)
            .setOnClickListener { startActivity(Intent(context,LoadMenu::class.java)) }
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
    fun getdata(key:String): String? {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("v1", Context.MODE_PRIVATE)
        val  data: SharedPreferences =  sharedPreferences
        var value = data.getString(key,"defoult_value")
        return value
    }
}