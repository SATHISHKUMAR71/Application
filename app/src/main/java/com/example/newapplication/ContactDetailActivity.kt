package com.example.newapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ContactDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_detail)
        findViewById<TextView>(R.id.textViewName).text =  intent.getStringExtra("Name")
        findViewById<TextView>(R.id.textViewID).text = intent.getStringExtra("ID")
        findViewById<TextView>(R.id.textViewDetail).text = intent.getStringExtra("Details")
    }
}