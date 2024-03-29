package com.education.vidhyalayaaa.Adapter

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
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
import com.education.vidhyalayaaa.model.HomeWorkModel
import com.squareup.picasso.Picasso
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeworkAdapter(var context: Context, val userList: ArrayList<HomeWorkModel>) : RecyclerView.Adapter<HomeworkAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    var TAG="@@HomeworkAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_homework, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val startdate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).submissionDate+" ").trim())
        val enddate =
            formateDateFromstring("dd/MM/yyyy", "dd, MMM yyyy",(userList.get(position).createdAt+" ").trim())


        Picasso.with(context)
            .load(Data.imageBaseUrl + userList[position].filePath)
            .into(holder.image, object: com.squareup.picasso.Callback {
                override fun onSuccess() {
                    //set animations here

                }

                override fun onError() {
                    holder.downloadDoc.visibility=View.GONE
                    holder.image.setImageResource(R.drawable.no_image)
                }
            })

        holder.title.setText(userList.get(position).title+" ")
        holder.description.setText(userList.get(position).description+" ")
//        holder.classe.setText(userList.get(position).cl+" ")
        holder.assing_date.setText(startdate)
        holder.submission_date.setText(userList.get(position).createdAt)
        holder.subject.setText(" "+userList.get(position).subjectName)

        holder.downloadDoc.setOnClickListener {
//            downloadFile(Data.imageBaseUrl + userList.get(position).filePath)
            Toast.makeText(context,Data.msg_image_download,Toast.LENGTH_LONG)
            AltexImageDownloader.writeToDisk(context, Data.imageBaseUrl + userList.get(position).filePath, Data.image_download_folder)
        }
        Log.d(TAG, "onBindViewHolder:1 "+userList.get(position).submissionDate)
        Log.d(TAG, "onBindViewHolder:2 "+userList.get(position).createdAt)
    }

    override fun getItemCount(): Int {
        return userList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image=itemView.findViewById<ImageView>(R.id.thumbnail)
        var title=itemView.findViewById<TextView>(R.id.title)
        var description=itemView.findViewById<TextView>(R.id.textview1)
        var subject=itemView.findViewById<TextView>(R.id.subject)
        var assing_date=itemView.findViewById<TextView>(R.id.assing_date)
        var submission_date=itemView.findViewById<TextView>(R.id.submission_date)
        var downloadDoc = itemView.findViewById<ImageView>(R.id.button)
    }

    fun formateDateFromstring(
        inputFormat: String?,
        outputFormat: String?,
        inputDate: String?
    ): String? {
        var parsed: Date? = null
        var outputDate = ""
        val df_input =
            SimpleDateFormat(inputFormat, Locale.getDefault())
        val df_output =
            SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            parsed = df_input.parse(inputDate)
            outputDate = df_output.format(parsed)
        } catch (e: ParseException) {
            Log.e("@@e", "ParseException - dateFormat"+e.message.toString())
        }
        return outputDate
    }


    fun downloadFile(uRl: String?) {
        val random = Random().nextInt(10000 - 1000) + 1000
        val direct = File(
            Environment.getExternalStorageDirectory()
                .toString() + "/SchoolDoc"
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
            .setAllowedOverRoaming(false).setTitle("Downloding ...")
            .setDescription("Student Pictures")
            .setDestinationInExternalPublicDir("/", "$random.jpg")
        mgr.enqueue(request)

        // Open Download Manager to view File progress
        Toast.makeText(context, "DownPlease wait.......", Toast.LENGTH_LONG).show()
        var intent= Intent(DownloadManager.ACTION_VIEW_DOWNLOADS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}