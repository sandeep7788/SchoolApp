package com.education.vidhyalayaaa.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.education.vidhyalayaaa.API.Data
import com.education.vidhyalayaaa.API.Services
import com.education.vidhyalayaaa.Adapter.MarkSheetAdapter
import com.education.vidhyalayaaa.R
import com.education.vidhyalayaaa.model.ExamTypeModel
import com.education.vidhyalayaaa.model.MarkSheetModel
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarkSheetActivity : AppCompatActivity() {

    var list = ArrayList<MarkSheetModel>()
    var examTypeList = ArrayList<ExamTypeModel>()
    lateinit var pb: android.app.AlertDialog
    lateinit var rv: RecyclerView
    lateinit var spinnerEducation: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marksheet)
        rv = findViewById(R.id.recyclerView)
        spinnerEducation = findViewById(R.id.spinnerEducation)
        pb = SpotsDialog.Builder().setContext(this@MarkSheetActivity).build()
        pb.setMessage("Please Wait ...")
        list = ArrayList()
        examTypeList = ArrayList()

        loadExamId()


        var i_backbutton: ImageView = findViewById(R.id.i_backbutton)
        i_backbutton.setOnClickListener { finish() }

//        spinnerEducation.onItemSelectedListener =  object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                loadMarkSheet(examTypeList.get(position).id.toString())
//
//            }
//
//            override fun onNothingSelected(p0: AdapterView<*>?) {
//
//            }
//        }

    }

    fun loadMarkSheet(examId: String) {
        pb.show()
        list.clear()
        var preferences = Services()
        val loginResponseCall: Call<List<MarkSheetModel>> = preferences.getMarkSheet()!!
            .getMarkSheet(
                getdata(Data.school_id),
                getdata(Data.id),
                getdata(Data.schoolSession),
                examId
            )

        loginResponseCall.enqueue(object : Callback<List<MarkSheetModel>> {
            override fun onFailure(call: Call<List<MarkSheetModel>>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(this@MarkSheetActivity, "Try Later", Toast.LENGTH_LONG).show()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<MarkSheetModel>>,
                response: Response<List<MarkSheetModel>>
            ) {
                pb.dismiss()
                if (response.isSuccessful) {


                    for (i in 0 until response.body()!!.size) {
                        list.addAll(listOf(response.body()!![i]))
                    }
                }

                val adapter = MarkSheetAdapter(
                    this@MarkSheetActivity.applicationContext,
                    list
                )
                rv.setBackgroundColor(resources.getColor(R.color.app))
                rv.layoutManager = GridLayoutManager(this@MarkSheetActivity, 1)
                rv.isNestedScrollingEnabled = false
                rv.setItemViewCacheSize(45)
                rv.isDrawingCacheEnabled = true
                rv.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
                rv.adapter = adapter
                adapter.notifyDataSetChanged()

                var maxTotal_ = 0
                list.forEach { it ->
                    maxTotal_ += it.maxmarks.toInt()
                }
                var txtMinTotal_ = 0
                list.forEach { it ->
                    txtMinTotal_ += it.minmarks.toInt()
                }
                var txtTotalObtain_ = 0
                list.forEach { it ->
                    txtTotalObtain_ += it.obtainMarks.toInt()
                }

                var layout_Marks: LinearLayout = findViewById<LinearLayout>(R.id.layout_Marks)
                var txtNoData: TextView = findViewById<TextView>(R.id.txtNoData)
                var txtPercentage: TextView = findViewById<TextView>(R.id.txtPercentage)
                var maxTotal: TextView = findViewById<TextView>(R.id.maxTotal)
                maxTotal.text = maxTotal_.toString()
                var txtMinTotal: TextView = findViewById<TextView>(R.id.txtMinTotal)
                txtMinTotal.text = txtMinTotal_.toString()
                var txtTotalObtain: TextView = findViewById<TextView>(R.id.txtTotalObtain)
                txtTotalObtain.text = txtTotalObtain_.toString()

                val percentage: Double = (txtTotalObtain_.toDouble() / maxTotal_.toDouble()) * 100


                var per = if ((percentage.toString()).length > 5) {
                    (percentage.toString()).substring(0,5)
                } else {
                    percentage.toString()
                }
                txtPercentage.text = per + " %"
                if (list.isEmpty()) {
                    layout_Marks.visibility = View.GONE
                    txtNoData.visibility = View.VISIBLE
                } else {
                    layout_Marks.visibility = View.VISIBLE
                    txtNoData.visibility = View.GONE
                }
            }
        })
    }

    fun loadExamId() {
        var preferences = Services()
        val loginResponseCall: Call<List<ExamTypeModel>> = preferences.getMarkSheet()!!
            .getExamType(getdata(Data.school_id))
        examTypeList.clear()
        loginResponseCall.enqueue(object : Callback<List<ExamTypeModel>> {
            override fun onFailure(call: Call<List<ExamTypeModel>>, t: Throwable) {
                pb.dismiss()
                Toast.makeText(this@MarkSheetActivity, "Try Later", Toast.LENGTH_LONG).show()
            }

            @SuppressLint("ResourceAsColor")
            override fun onResponse(
                call: Call<List<ExamTypeModel>>,
                response: Response<List<ExamTypeModel>>
            ) {
                pb.dismiss()
                if (response.isSuccessful) {


                    for (i in 0 until response.body()!!.size) {
                        examTypeList.addAll(listOf(response.body()!![i]))
                    }
                    setSpinner(spinnerEducation)


                }

                var layout_Marks: LinearLayout = findViewById<LinearLayout>(R.id.layout_Marks)
                var txtNoData: TextView = findViewById<TextView>(R.id.txtNoData)

                if (examTypeList.isEmpty()) {
                    layout_Marks.visibility = View.GONE
                    txtNoData.visibility = View.VISIBLE
                } else {
                    layout_Marks.visibility = View.VISIBLE
                    txtNoData.visibility = View.GONE
                }

            }
        })
    }

    fun setSpinner(spinner: Spinner) {


        val item = ArrayList<String>()

        examTypeList.forEach { it ->
            item.add(it.exam_name)
        }

        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                R.layout.simple_spinner_item, item
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    loadMarkSheet(examTypeList.get(position).id.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action

                }
            }
        }
    }


    fun getdata(key: String): String {
        val sharedPreferences: SharedPreferences =
            getSharedPreferences("v1", Context.MODE_PRIVATE)
        val data: SharedPreferences = sharedPreferences
        var value = data.getString(key, "")
        return value!!
    }

    override fun onDestroy() {

        this.onVisibleBehindCanceled()
        super.onDestroy()
    }
}
