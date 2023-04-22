package com.example.myhealth

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
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
        val duration = 500L

        val restButton = findViewById<Button>(R.id.restButton)
        val timeButton60 = findViewById<Button>(R.id.time60)
        val timeButton90 = findViewById<Button>(R.id.time90)
        val timeButton150 = findViewById<Button>(R.id.time150)
        val timeButton180 = findViewById<Button>(R.id.time180)

        val setText = findViewById<TextView>(R.id.setText)

        setText.text = Set.set.toString()

        restButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(duration)
            }
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 120*1000)
            startActivity(intent)
            finish()
        }

        timeButton60.setOnClickListener {
            it.animate()
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(100)
                .withEndAction {
                    it.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(duration)
            }
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 60*1000)
            startActivity(intent)
            finish()
        }

        timeButton90.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(duration)
            }
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 90*1000)
            startActivity(intent)
            finish()
        }

        timeButton150.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(duration)
            }
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 150*1000)
            startActivity(intent)
            finish()
        }

        timeButton180.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(duration)
            }
            val intent = Intent(this, TimeActivity::class.java).putExtra("time", 180*1000)
            startActivity(intent)
            finish()
        }

    }
}