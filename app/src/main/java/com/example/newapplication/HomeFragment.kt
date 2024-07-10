package com.example.newapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment(val contactList:MutableList<Contacts>) : Fragment() {
//    class HomeFragment() : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view =  inflater.inflate(R.layout.fragment_home, container, false)
        val adapter = ContactAdapter(contactList)
        val rv = view.findViewById<RecyclerView>(R.id.recyclerView)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(rv.context, LinearLayoutManager.VERTICAL)
        rv.addItemDecoration(dividerItemDecoration)

        return view
    }

}