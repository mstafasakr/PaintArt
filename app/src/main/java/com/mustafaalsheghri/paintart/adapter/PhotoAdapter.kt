package com.mustafaalsheghri.paintart.adapter

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*
import com.mustafaalsheghri.paintart.R
import com.mustafaalsheghri.paintart.model.FavDB
import com.mustafaalsheghri.paintart.model.Model
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter(var context: Context,var list: ArrayList<Model>): RecyclerView.Adapter<PhotoAdapter.viewHolder>() {
    private var favDB: FavDB? = null

    inner class viewHolder(view:View):RecyclerView.ViewHolder(view) {
         var image : ImageView
         var btn_fav :ImageButton
         var textLike:TextView
         var textName:TextView
         lateinit var dwonload:ImageButton

        init {
            image = view.findViewById(R.id.view_image)
            btn_fav = view.findViewById(R.id.add_favorite)
            textLike = view.findViewById(R.id.likeCountTextView)
            textName = view.findViewById(R.id.text_name)
            dwonload = view.findViewById(R.id.download_image)
            btn_fav.setOnClickListener {
                val position = adapterPosition
                val coffeeItem = list[position]
                likeClick(coffeeItem, btn_fav, textLike)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        favDB = FavDB(context)
        //create table on first
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val firstStart = prefs.getBoolean("firstStart", true)
        if (firstStart) {
            createTableOnFirstStart()
        }
        val view = LayoutInflater.from(context).inflate(R.layout.item_photo,parent,false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val model = list[position]
        readCursorData(model,holder)
        holder.textName.text = model.title
        holder.dwonload.setOnClickListener {

            Toast.makeText(context,model.downloadUrl.toString(),Toast.LENGTH_LONG).show()
        }
        Glide.with(context)
                .load(model.imageUrl)
                .into(holder.image)

    }
    override fun getItemCount(): Int {

        return list.size
    }
    private fun createTableOnFirstStart() {
        favDB!!.insertEmpty()
        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean("firstStart", false)
        editor.apply()
    }
    private fun readCursorData(coffeeItem:Model, viewHolder:viewHolder) {
            val cursor = favDB!!.read_all_data(FavDB.KEY_ID)
            val db = favDB!!.readableDatabase
            try {
                while (cursor.moveToNext()) {
                    val item_fav_status = cursor.getString(cursor.getColumnIndex(FavDB.FAVORITE_STATUS))
                    coffeeItem.favStatus = item_fav_status
                    //check fav status
                    if (item_fav_status != null && item_fav_status == "1") {
                        viewHolder.btn_fav.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
                    } else if (item_fav_status != null && item_fav_status == "0") {
                        viewHolder.btn_fav.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
                    }
                }
            } finally {

                cursor.close()
                db.close()
            }
        }


    // like click
    private fun likeClick(coffeeItem: Model, favBtn: ImageButton, textLike: TextView) {
        if (FavDB.FAVORITE_STATUS == "0") {
            FavDB.FAVORITE_STATUS = "1"
            favDB!!.insertIntoTheDatabase(coffeeItem.imageUrl!!,
                     coffeeItem.favStatus!!)
            favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp)
           // favBtn.isSelected = true
        } else if (FavDB.FAVORITE_STATUS == "1") {
            FavDB.FAVORITE_STATUS = "0"
           // favDB!!.remove_fav(coffeeItem.id!!)
            favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp)
            //favBtn.isSelected = false
        }
    }
}