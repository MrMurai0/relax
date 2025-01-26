package com.example.relax25

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class TimerActivity : AppCompatActivity() {

    private lateinit var minutesEditText: EditText
    private lateinit var secondsEditText: EditText
    private lateinit var timerTextView: TextView
    private lateinit var startButton: Button
    private lateinit var stopButton: Button
    private var countDownTimer: CountDownTimer? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        minutesEditText = findViewById(R.id.minutesEditText)
        secondsEditText = findViewById(R.id.secondsEditText)
        timerTextView = findViewById(R.id.timerTextView)
        startButton = findViewById(R.id.startButton)
        stopButton = findViewById(R.id.stopButton)

        startButton.setOnClickListener { startTimer() }
        stopButton.setOnClickListener { stopTimer() }

        mediaPlayer = MediaPlayer.create(this, R.raw.melodia)
    }

    private fun startTimer() {
        val minutes = minutesEditText.text.toString().toIntOrNull() ?: 0
        val seconds = secondsEditText.text.toString().toIntOrNull() ?: 0
        val totalMillis = (minutes * 60 + seconds) * 1000L

        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutesLeft = millisUntilFinished / (1000 * 60)
                val secondsLeft = (millisUntilFinished / 1000) % 60
                timerTextView.text = String.format("%02d:%02d", minutesLeft, secondsLeft)
            }

            override fun onFinish() {
                timerTextView.text = "00:00"
                mediaPlayer?.start()
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        timerTextView.text = "00:00"
        mediaPlayer?.pause()
        mediaPlayer?.seekTo(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        countDownTimer?.cancel()
    }
}