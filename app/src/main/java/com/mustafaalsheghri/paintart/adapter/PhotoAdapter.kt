package com.mustafaalsheghri.paintart.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mustafaalsheghri.paintart.R
import com.mustafaalsheghri.paintart.model.Model
import kotlinx.android.synthetic.main.item_photo.view.*
import kotlinx.android.synthetic.main.view_image_item.view.*

class PhotoAdapter(var context: Context,var list: ArrayList<Model>):RecyclerView.Adapter<PhotoAdapter.viewHolder>() {

    class viewHolder(view:View):RecyclerView.ViewHolder(view) {
        var image = view.view_image
        var card = view.card_view

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val view = LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val model = list[position]
        Glide.with(context)
                .load(model.imageUrl)
                .into(holder.image)
        holder.card.setOnClickListener {
            View_Data(position)
        }

    }

    override fun getItemCount(): Int {

        return list.size
    }

    private fun View_Data(position: Int){
        val model = list[position]
        val alertDialog = AlertDialog.Builder(this.context)
        val view = LayoutInflater.from(this.context).inflate(R.layout.view_image_item,null)
        alertDialog.setView(view)
        val alert = alertDialog.create()
        alert.show()
        val image = view.view_image_2
        val favorite = view.add_favorite
        val home_secreen = view.home_secreen
        val look_screen = view.look_screen
        val download_image = view.download_image
        Glide.with(this.context)
                .load(model.imageUrl)
                .into(image)
        download_image.setOnClickListener {  }
        favorite.setOnClickListener {  }
        home_secreen.setOnClickListener {  }
        look_screen.setOnClickListener {  }

    }
}