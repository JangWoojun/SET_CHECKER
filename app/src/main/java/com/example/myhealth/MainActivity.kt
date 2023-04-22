package com.example.myhealth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import com.example.myhealth.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val duration = 50L

        val restButton = findViewById<Button>(R.id.restButton)
        val timeButton60 = findViewById<Button>(R.id.time60)
        val timeButton90 = findViewById<Button>(R.id.time90)
        val timeButton150 = findViewById<Button>(R.id.time150)
        val timeButton180 = findViewById<Button>(R.id.time180)
        val setText = findViewById<TextView>(R.id.setText)

        setText.text = Set.set.toString()

        restButton.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 120*1000)
            startActivity(intent)
            finish()
        }

        timeButton60.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 60*1000)
            startActivity(intent)
            finish()
        }

        timeButton90.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 90*1000)
            startActivity(intent)
            finish()
        }

        timeButton150.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 150*1000)
            startActivity(intent)
            finish()
        }

        timeButton180.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 180*1000)
            startActivity(intent)
            finish()
        }

    }
}