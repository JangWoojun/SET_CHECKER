package com.sus.myhealth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PowerManager
import android.os.Vibrator
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView

class TimeActivity : AppCompatActivity() {
    private lateinit var setText: TextView
    private lateinit var toggleButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var timeText: TextView

    private var toggleChk = true
    private var viewTime = 0

    private val time = Data.time
    private val handler = Handler()
    private val vibrator by lazy { getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }
    private val powerManager by lazy { getSystemService(Context.POWER_SERVICE) as PowerManager }
    private lateinit var wakeLock: PowerManager.WakeLock

    private val runnable = Runnable {
        Data.set++
        handler.removeCallbacks(runnable2)
        val pattern = longArrayOf(0, 300, 100, 300, 100, 300)
        vibrator.vibrate(pattern, 1)
        val intent = Intent(this@TimeActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private val runnable2 = object : Runnable {
        override fun run() {
            viewTime++
            if ((viewTime * 1000) <= time) {
                val (minutes, seconds) = convertToMinutesAndSeconds(viewTime)
                timeText.text = "$minutes:$seconds"
                handler.postDelayed(this, 1000)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "com.sus.myhealth:MyWakelockTag")
        wakeLock.acquire(10*60*1000L)

        setContentView(R.layout.activity_time)

        setText = findViewById(R.id.setText)
        toggleButton = findViewById(R.id.toggleButton)
        stopButton = findViewById(R.id.closeButton)
        timeText = findViewById(R.id.timeText)

        setText.text = "${Data.set} SET"

        handler.postDelayed(runnable, time.toLong())
        handler.postDelayed(runnable2, 1000)

        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            handler.removeCallbacks(runnable2)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        toggleButton.setOnClickListener {
            if (toggleChk) {
                toggleChk = false
                handler.removeCallbacks(runnable)
                handler.removeCallbacks(runnable2)
                toggleButton.setBackgroundResource(R.drawable.radius_img)
                toggleButton.setImageResource(R.drawable.play)
            } else {
                toggleChk = true
                val reTime = viewTime * 1000
                handler.postDelayed(runnable, time - reTime.toLong())
                handler.postDelayed(runnable2, 1000)
                toggleButton.setBackgroundResource(R.drawable.radius_img_red)
                toggleButton.setImageResource(R.drawable.stop)
            }
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onDestroy() {
        super.onDestroy()
        wakeLock.release()
    }
    fun convertToMinutesAndSeconds(timeInSeconds: Int): Pair<String, String> {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return Pair(minutes.toString().padStart(2, '0'), seconds.toString().padStart(2, '0'))
    }
}

