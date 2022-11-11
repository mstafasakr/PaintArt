package com.mustafaalsheghri.paintart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.mustafaalsheghri.paintart.adapter.PhotoAdapter
import com.mustafaalsheghri.paintart.model.Model
import kotlinx.android.synthetic.main.fragment_photo.*

class PhotoFragment : Fragment() {

    private var mRef :DatabaseReference? = null
    private var list:ArrayList<Model>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRef = FirebaseDatabase.getInstance().getReference("Images")
        list = ArrayList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photo, container, false)
    }

    override fun onStart() {
        super.onStart()
        mRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list!!.clear()
                for (n in snapshot.children) {
                    val add = n.getValue(Model::class.java)
                    list!!.add(add!!)
                }
                rec_photo.apply {
//                    setHasFixedSize(true)
                    layoutManager = LinearLayoutManager(requireActivity())
                    adapter = PhotoAdapter(requireActivity(), list!!)
                }

            }

            override fun onCancelled(error: DatabaseError) {
            }

        })

    }
}