package com.ashutosh.wallpaperapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ashutosh.wallpaperapp.ui.fragments.CategoryFragment
import com.ashutosh.wallpaperapp.ui.fragments.FavoritesFragment
import com.ashutosh.wallpaperapp.ui.fragments.HomeFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {

        return when(position){
            0-> HomeFragment()
            1-> CategoryFragment()
            2-> FavoritesFragment()
            else -> Fragment()
        }
    }
}