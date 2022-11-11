package com.mustafaalsheghri.paintart.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {
    var fragment = ArrayList<Fragment>()
    var tab_List = ArrayList<String>()
    fun addFragment(fragment: Fragment,tabTitle:String){
        this.fragment.add(fragment)
        this.tab_List.add(tabTitle)
    }
    override fun getItem(position: Int): Fragment {
        return this.fragment[position]
    }

    override fun getCount(): Int {
        return fragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return this.tab_List[position]
    }
}