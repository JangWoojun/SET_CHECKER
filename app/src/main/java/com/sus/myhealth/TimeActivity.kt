package com.sus.myhealth

import android.content.Context
import android.content.Intent
import android.os.*
import androidx.appcompat.app.AppCompatActivity
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
    private lateinit var wakeLock1: PowerManager.WakeLock


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager

        val wakeLock2 = powerManager.newWakeLock(
            PowerManager.FULL_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
            "com.sus.myhealth:MyWakelockTag"
        )

        val runnable2 = object : Runnable {
            override fun run() {
                viewTime++
                if ((viewTime * 1000) <= time) {
                    val (minutes, seconds) = convertToMinutesAndSeconds(viewTime)
                    timeText.text = "$minutes:$seconds"
                    handler.postDelayed(this, 1000)
                }
            }
        }

        val runnable = Runnable {
            if (!powerManager.isInteractive) {
                wakeLock2.acquire(5000)
            }
            Data.set++
            handler.removeCallbacks(runnable2)
            handler.postDelayed({
                    val pattern = longArrayOf(100, 300, 100, 300)
                    vibrator.vibrate(pattern, -1)
                }, 500)
            val intent = Intent(this@TimeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        wakeLock1 = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "com.sus.myhealth:MyWakelockTag")
        wakeLock1.acquire(120*60*1000L)

        setContentView(R.layout.activity_time)

        setText = findViewById(R.id.setText)
        toggleButton = findViewById(R.id.toggleButton)
        stopButton = findViewById(R.id.closeButton)
        timeText = findViewById(R.id.timeText)

        setText.text = "${Data.set} SET"

        handler.postDelayed(runnable, time.toLong())
        handler.postDelayed(runnable2, 1000)

        stopButton.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(30L, VibrationEffect.DEFAULT_AMPLITUDE))
            handler.removeCallbacks(runnable)
            handler.removeCallbacks(runnable2)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        toggleButton.setOnClickListener {
            vibrator.vibrate(VibrationEffect.createOneShot(30L, VibrationEffect.DEFAULT_AMPLITUDE))

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
        wakeLock1.release()
    }
    fun convertToMinutesAndSeconds(timeInSeconds: Int): Pair<String, String> {
        val minutes = timeInSeconds / 60
        val seconds = timeInSeconds % 60
        return Pair(minutes.toString().padStart(2, '0'), seconds.toString().padStart(2, '0'))
    }
}

