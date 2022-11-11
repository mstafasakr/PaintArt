package com.mustafaalsheghri.paintart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaalsheghri.paintart.adapter.FavoritAdapter
import com.mustafaalsheghri.paintart.model.FavoritModel
import com.mustafaalsheghri.paintart.model.FavDB
import kotlinx.android.synthetic.main.fragment_favorit.*
import kotlin.collections.ArrayList


class FavoritFragment : Fragment() {

    private var favDB: FavDB? = null
    private var favItemList: MutableList<FavoritModel>? = null
    private var favAdapter: FavoritAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favItemList = ArrayList()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorit, container, false)
        return view

    }

    override fun onStart() {
        super.onStart()
        loadData()
    }


    private fun loadData() {
        favItemList?.clear()

            favAdapter = FavoritAdapter(requireActivity(), favItemList!!)
            rec_fav.layoutManager = LinearLayoutManager(requireActivity())
            rec_fav!!.adapter = favAdapter
        }

    }

