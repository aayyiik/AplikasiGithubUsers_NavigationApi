package com.submission.picodiploma.aplikasigithubusers_navigationapi.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailUserAdapter (
    fg: FragmentActivity,
    private val fragment: MutableList<Fragment> ):
    FragmentStateAdapter (fg) {
    override fun getItemCount(): Int = fragment.size

    override fun createFragment(position: Int): Fragment = fragment[position]

}