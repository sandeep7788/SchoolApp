package com.education.vidhyalaya.Adapter

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalaya.R
import com.education.vidhyalaya.model.AnswerSheetModel
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*

class AnswereSheetAdapter(var context: Context, val userList: ArrayList<AnswerSheetModel>) : RecyclerView.Adapter<AnswereSheetAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    var MULTIPLE_PERMISSIONS = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_answere, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("@@adapter", userList.get(position).id)
        holder.txt_sub.setText("Subject :"+userList.get(position).subjectName+" ")
//        holder.txt_faculte.setText("Name :- "+userList.get(position).firstName+" "+userList.get(position).lastName+" ")
        holder.txt_faculte.setText("Faculty :"+userList.get(position).facultyname)
        holder.txt_remark.setText("Remark :"+userList.get(position).remark)
        holder.txt_date.setText("Date :"+userList.get(position).createdAt+" ")

     /*   Picasso.with(context).load("http://vidhyalaya.co.in/sspanel/" + userList[position].filePath)
            .error(R.drawable.no_image)
            .resize(290, 290)
            .into(holder.image)*/


        Picasso.with(context)
            .load("http://vidhyalaya.co.in/sspanel/" + userList[position].filePath)
            .into(holder.image, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                    //set animations here

                }

                override fun onError() {
                    holder.btn_download.visibility=View.GONE
                    holder.image.setImageResource(R.drawable.no_image)
                }
            })


        holder.btn_download.setOnClickListener {
            downloadFile("http://vidhyalaya.co.in/sspanel/" + userList.get(position).filePath)
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
        var btn_download=itemView.findViewById<Button>(R.id.btn_download)
    }




    fun downloadFile(uRl: String?) {
        val random = Random().nextInt(10000 - 1000) + 1000
        val direct = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/AnhsirkDasarp"
        )
        if (!direct.exists()) {
            direct.mkdirs()
        }
        val mgr =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(uRl)
        val request = DownloadManager.Request(
            downloadUri
        )
        request.setAllowedNetworkTypes(
            DownloadManager.Request.NETWORK_WIFI
                    or DownloadManager.Request.NETWORK_MOBILE
        )
            .setAllowedOverRoaming(false).setTitle("Downloding")
            .setDescription("Student Pictures")
            .setDestinationInExternalPublicDir("/File", "$random.jpg")
        mgr.enqueue(request)

        // Open Download Manager to view File progress
        Toast.makeText(context, "Downloading...", Toast.LENGTH_LONG).show()
        var intent= Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}