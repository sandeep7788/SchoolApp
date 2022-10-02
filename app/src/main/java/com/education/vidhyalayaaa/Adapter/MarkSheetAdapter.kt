package com.education.vidhyalayaaa.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalayaaa.R
import com.education.vidhyalayaaa.model.MarkSheetModel

class MarkSheetAdapter(var context: Context, val userList: ArrayList<MarkSheetModel>) :
    RecyclerView.Adapter<MarkSheetAdapter.ViewHolder>() {

    var prefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.mark_sheet_layout, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.txtSubject.text = "" + userList.get(position).subjectName.toString()
        holder.txtMin.text = "" + userList.get(position).minmarks.toString()
        holder.txtMax.text = "" + userList.get(position).maxmarks.toString()
        holder.txtObtainMarks.text = "" + userList.get(position).obtainMarks.toString()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtSubject = itemView.findViewById<TextView>(R.id.txtSubject)
        var txtMin = itemView.findViewById<TextView>(R.id.txtMin)
        var txtMax = itemView.findViewById<TextView>(R.id.txtMax)
        var txtObtainMarks = itemView.findViewById<TextView>(R.id.txtObtainMarks)
    }
}