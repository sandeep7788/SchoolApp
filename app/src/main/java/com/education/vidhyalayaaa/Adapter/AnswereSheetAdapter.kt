package com.education.vidhyalayaaa.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.artjimlop.altex.AltexImageDownloader
import com.education.vidhyalayaaa.API.Data
import com.education.vidhyalayaaa.R
import com.education.vidhyalayaaa.model.AnswerSheetModel
import com.squareup.picasso.Picasso

class AnswereSheetAdapter(var context: Context, val userList: ArrayList<AnswerSheetModel>) :
    RecyclerView.Adapter<AnswereSheetAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    var MULTIPLE_PERMISSIONS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_answere, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("@@adapter", userList.get(position).id)
        holder.txt_sub.text = "Subject :" + userList.get(position).subjectName + " "
//        holder.txt_faculte.setText("Name :- "+userList.get(position).firstName+" "+userList.get(position).lastName+" ")
        holder.txt_faculte.text = "Faculty :" + userList.get(position).facultyname
        holder.txt_remark.text = "Remark :" + userList.get(position).remark
        holder.txt_date.text = "Date :" + userList.get(position).createdAt + " "

        /*   Picasso.with(context).load(Data.imageBaseUrl + userList[position].filePath)
               .error(R.drawable.no_image)
               .resize(290, 290)
               .into(holder.image)*/


        Picasso.with(context)
            .load(Data.imageBaseUrl + userList[position].filePath)
            .into(holder.image, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    //set animations here

                }

                override fun onError() {
                    holder.btn_download.visibility = View.GONE
                    holder.image.setImageResource(R.drawable.no_image)
                }
            })


        holder.btn_download.setOnClickListener {
            Toast.makeText(context, Data.msg_image_download, Toast.LENGTH_LONG)
            AltexImageDownloader.writeToDisk(
                context,
                Data.imageBaseUrl + userList.get(position).filePath,
                Data.image_download_folder
            )
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image = itemView.findViewById<ImageView>(R.id.image)
        var txt_sub = itemView.findViewById<TextView>(R.id.txt_sub)
        var txt_faculte = itemView.findViewById<TextView>(R.id.txt_faculte)
        var txt_date = itemView.findViewById<TextView>(R.id.txt_date)
        var txt_remark = itemView.findViewById<TextView>(R.id.txt_remark)
        var btn_download = itemView.findViewById<ImageView>(R.id.btn_download)
    }
}