package com.education.schoolapp.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.education.schoolapp.Fragments.AccountFragment
import com.education.schoolapp.Fragments.ChatFragment
import com.education.schoolapp.Fragments.HomeFragment

class MyAdapter(private val myContext: Context, fm: FragmentManager, internal var totalTabs: Int) : FragmentPagerAdapter(fm) {

    // this is for fragment tabs
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return HomeFragment()
            }
            1 -> {
                return ChatFragment()
            }
            2 -> {
                return AccountFragment()
            }
            else -> return HomeFragment()
        }
    }

    // this counts total number of tabs
    override fun getCount(): Int {
        return totalTabs
    }
}
