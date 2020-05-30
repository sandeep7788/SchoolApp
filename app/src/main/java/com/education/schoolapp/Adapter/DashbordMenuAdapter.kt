package com.education.schoolapp.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.education.schoolapp.API.MomentApi
import com.education.schoolapp.R
import com.squareup.picasso.Picasso
import java.util.*

class DashbordMenuAdapter(var context: Context, val userList: ArrayList<MomentApi>) : RecyclerView.Adapter<DashbordMenuAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_dashbordmenu, parent, false)








        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Log.e("@@adapter",userList.get(position).id)
        holder.name.setText(userList.get(position).title)



        Picasso.with(context).load("http://skoolstarr.com/sspanel/"+userList[position].filePath)
            .error(R.drawable.ic_account_circle_black_24dp)
            .resize(140,140)
            .into(holder.img)



        /*holder.cv.setOnClickListener {
            Log.e("@@adapter",userList.get(position).schoolName)
            setdata(

                userList.get(position).id,userList.get(position).userid,userList.get(position).classid,userList.get(position).class_,userList.get(position).section,
                userList.get(position).student,userList.get(position).studentImage,userList.get(position).schoolId,userList.get(position).schoolName,userList.get(position).staffid,userList.get(position).staffuserid,
                userList.get(position).classteacher,userList.get(position).facultyPhone)
                context.startActivity(Intent(context,
                    Dashbord::class.java))
        }*/
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name=itemView.findViewById<TextView>(R.id.textview)
        var img=itemView.findViewById<ImageView>(R.id.img1)
        var cv=itemView.findViewById<CardView>(R.id.cv)
    }

    fun setdata(id:String,Userid:String,classid:String,classe:String,section:String,Student:String,student_image:String,
                school_id:String,school_name:String,staffid:String,staffuserid:String,Classteacher:String,FacultyPhone:String)
    {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("v1", Context.MODE_PRIVATE)
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
    }
}