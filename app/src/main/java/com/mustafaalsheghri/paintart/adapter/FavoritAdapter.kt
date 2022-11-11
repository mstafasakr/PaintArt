package com.mustafaalsheghri.paintart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafaalsheghri.paintart.R
import com.mustafaalsheghri.paintart.model.FavoritModel
import com.mustafaalsheghri.paintart.model.FavDB
import kotlinx.android.synthetic.main.item_favorite.view.*

class FavoritAdapter(var context: Context, var list: MutableList<FavoritModel>):RecyclerView.Adapter<FavoritAdapter.ViewHolder>() {

    private var favDB: FavDB? = null
    class ViewHolder(view:View):RecyclerView.ViewHolder(view) {
        var image = view.fav_image
        var card = view.card_fav
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_favorite,parent,false)
        favDB = FavDB(context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        Glide.with(context)
                .load(model.imageUrl)
                .into(holder.image)
        holder.card.setOnClickListener {


        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


}