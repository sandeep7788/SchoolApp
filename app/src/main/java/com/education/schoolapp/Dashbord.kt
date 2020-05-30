package com.education.schoolapp

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.education.schoolapp.Adapter.MyAdapter
import com.google.android.material.tabs.TabLayout

class Dashbord : AppCompatActivity()
{
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    private val instance: Dashbord? = null
    lateinit var mDrawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashbord)
        mDrawerLayout=findViewById(R.id.drawer_layout)
        tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        viewPager = findViewById<ViewPager>(R.id.viewPager)

        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_home_black_24dp))
        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_chat_black_24dp))
        tabLayout!!.addTab(tabLayout!!.newTab().setIcon(R.drawable.ic_account_circle_black_24dp))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = MyAdapter(
            this,
            supportFragmentManager,
            tabLayout!!.tabCount
        )
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }
    fun open_navigation_drawable()
    {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
    fun getInstance(): Dashbord? {
        return instance
    }


}