package com.sus.myhealth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.TextView
import com.sus.myhealth.databinding.ActivityMainBinding

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restButton = findViewById<Button>(R.id.restButton)
        val timeButton60 = findViewById<Button>(R.id.time60)
        val timeButton90 = findViewById<Button>(R.id.time90)
        val timeButton150 = findViewById<Button>(R.id.time150)
        val timeButton180 = findViewById<Button>(R.id.time180)
        val setText = findViewById<TextView>(R.id.setText)
        val outButton = findViewById<Button>(R.id.outButton)

        setText.text = Data.set.toString()

        restButton.setOnClickListener {
            Data.time = 120*1000
            moveTimer()
        }

        timeButton60.setOnClickListener {
            Data.time = 60*1000
            moveTimer()
        }

        timeButton90.setOnClickListener {
            Data.time = 90*1000
            moveTimer()
        }

        timeButton150.setOnClickListener {
            Data.time = 150*1000
            moveTimer()
        }

        timeButton180.setOnClickListener {
            Data.time = 180*1000
            moveTimer()
        }

        outButton.setOnClickListener {
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            vibrator.vibrate(VibrationEffect.createOneShot(50L, VibrationEffect.DEFAULT_AMPLITUDE))
            moveTaskToBack(true)
            finishAndRemoveTask()
            android.os.Process.killProcess(android.os.Process.myPid())
        }

    }

    private fun moveTimer() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(30L, VibrationEffect.DEFAULT_AMPLITUDE))

        val intent = Intent(this, TimeActivity::class.java)
        startActivity(intent)
        finish()
    }
}