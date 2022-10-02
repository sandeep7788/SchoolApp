package com.education.vidhyalayaaa.Activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.education.vidhyalayaaa.R

class HelpPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_page)

        var i_backbutton=findViewById<ImageView>(R.id.i_backbutton)
        i_backbutton.setOnClickListener {
            finish()
        }
        var img_make_call=findViewById<ImageView>(R.id.img_make_call)
        img_make_call.setOnClickListener {
            makeCall(this,"9828775444")


        }
        var MyReceiver: BroadcastReceiver?= null;
        MyReceiver = com.education.vidhyalayaaa.helper.MyReceiver()
        registerReceiver(MyReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }
    fun makeCall(context: Context, mob: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)

            intent.data = Uri.parse("tel:$mob")
            context.startActivity(intent)
        } catch (e: java.lang.Exception) {
            Toast.makeText(context,
                "Unable to call at this time", Toast.LENGTH_SHORT).show()
        }
    }
}