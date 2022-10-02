package com.education.vidhyalayaaa.Adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.education.vidhyalayaaa.Fragments.AccountFragment
import com.education.vidhyalayaaa.Fragments.ChatFragment
import com.education.vidhyalayaaa.Fragments.HomeFragment

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
