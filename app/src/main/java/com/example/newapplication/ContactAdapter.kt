package com.example.newapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactAdapter(private var contactList:List<Contacts>) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(contactView: View) : RecyclerView.ViewHolder(contactView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout,parent,false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.itemView.apply {
            this.findViewById<TextView>(R.id.textViewName).text = contactList[position].name
            this.findViewById<TextView>(R.id.textViewPhone).text = contactList[position].id.toString()
            this.setOnClickListener{
                val intent = Intent(context,ContactDetailActivity::class.java)
                intent.putExtra("Name",contactList[position].name)
                intent.putExtra("ID",contactList[position].id.toString())
                intent.putExtra("Details","NO DETAILS AVAILABLE...")
                context.startActivity(intent)
//                this.transitionName = contactList[position].id.toString()
                println("OnBind ${holder.itemView.id}")
            }
        }
    }
}