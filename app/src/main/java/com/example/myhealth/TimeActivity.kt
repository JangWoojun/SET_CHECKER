package com.example.myhealth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Vibrator
import android.widget.ImageButton
import android.widget.TextView

class TimeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time)

        val pattern = longArrayOf(0, 300, 100, 300, 100, 300)
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        val setText = findViewById<TextView>(R.id.setText)
        val toggleButton = findViewById<ImageButton>(R.id.toggleButton)
        val stopButton = findViewById<ImageButton>(R.id.closeButton)
        val timeText = findViewById<TextView>(R.id.timeText)

        setText.text = "${Set.set} SET"
        var toggleChk = true

        val time = intent.getIntExtra("time", 120000)
        var viewTime = 0

        val handler = Handler()
        val runnable2 = object : Runnable {
            override fun run() {
                viewTime++
                if ((viewTime*1000) <= time) {
                    val (minutes, seconds) = convertToMinutesAndSeconds(viewTime)
                    timeText.text = "$minutes:$seconds"
                    handler.postDelayed(this, 1000)
                }
            }
        }
        val runnable = Runnable {
            Set.set++
            handler.removeCallbacks(runnable2)
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val pattern = longArrayOf(0, 300, 100, 300, 100, 300)
            vibrator.vibrate(pattern, 3)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        handler.postDelayed(runnable, time.toLong())
        handler.postDelayed(runnable2, 1000)

        stopButton.setOnClickListener{
            handler.removeCallbacks(runnable)
            handler.removeCallbacks(runnable2)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        toggleButton.setOnClickListener{
            if (toggleChk) {
                toggleChk = false
                handler.removeCallbacks(runnable)
                handler.removeCallbacks(runnable2)
                toggleButton.setBackgroundResource(R.drawable.radius_img)
                toggleButton.setImageResource(R.drawable.play)
            } else {
                toggleChk = true
                val reTime = viewTime*1000
                handler.postDelayed(runnable, time-reTime.toLong())
                handler.postDelayed(runnable2, 1000)
                toggleButton.setBackgroundResource(R.drawable.radius_img_red)
                toggleButton.setImageResource(R.drawable.stop)
            }
        }
    }

    fun convertToMinutesAndSeconds(timeInSeconds: Int): Pair<String, String> {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return Pair(minutes.toString().padStart(2, '0'), seconds.toString().padStart(2, '0'))
    }
}

