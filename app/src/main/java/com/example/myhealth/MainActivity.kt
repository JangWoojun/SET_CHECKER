package com.example.myhealth

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.myhealth.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var time = 120*1000

        val restButton = findViewById<Button>(R.id.restButton)
        val timeButton90 = findViewById<Button>(R.id.time90)
        val timeButton120 = findViewById<Button>(R.id.time120)
        val timeButton150 = findViewById<Button>(R.id.time150)
        val timeButton180 = findViewById<Button>(R.id.time180)

        restButton.setOnClickListener {
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", time)
            startActivity(intent)
        }

        timeButton90.setOnClickListener {
            time = 90*1000
        }

        timeButton120.setOnClickListener {
            time = 120*1000
        }

        timeButton150.setOnClickListener {
            time = 150*1000
        }

        timeButton180.setOnClickListener {
            time = 180*1000
        }

    }
}