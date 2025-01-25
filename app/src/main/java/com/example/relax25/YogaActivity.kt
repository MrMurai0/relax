package com.example.relax25

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.relax25.R
import kotlinx.coroutines.*


class YogaActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var poseImageView: ImageView
    private lateinit var poseDescriptionTextView: TextView
    private lateinit var nextButton: Button
    private var countDownTimer: CountDownTimer? = null
    private var mediaPlayer: MediaPlayer? = null
    private var currentPoseIndex = 0
    private val workout = listOf(
        Workout(R.drawable.play, "Поза 1", "Описание 1", 60000),
        Workout(R.drawable.play, "Поза 2", "Описание 2", 60000),
        Workout(R.drawable.play, "Поза 3", "Описание 3", 60000)
        // ... добавить другие позы
    )
    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)

        timerTextView = findViewById(R.id.timerTextView)
        poseImageView = findViewById(R.id.poseImageView)
        poseDescriptionTextView = findViewById(R.id.poseDescriptionTextView)
        nextButton = findViewById(R.id.nextButton)

        nextButton.setOnClickListener { nextPose() }


        startWorkout()
    }

    private fun startWorkout() {
        if (currentPoseIndex < workout.size) {
            val currentWorkout = workout[currentPoseIndex]
            poseImageView.setImageResource(currentWorkout.imageResId)
            poseDescriptionTextView.text = currentWorkout.description
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(this, R.raw.d) // Replace with your music
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()

            countDownTimer = object : CountDownTimer(currentWorkout.duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timerTextView.text = formatTime(millisUntilFinished)
                }

                override fun onFinish() {
                    nextPose()
                }
            }.start()
        } else {
            finish()
        }
    }
    private fun nextPose(){
        countDownTimer?.cancel()
        currentPoseIndex++
        startWorkout()
    }


    private fun formatTime(milliseconds: Long): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    data class Workout(val imageResId: Int, val title: String, val description: String, val duration: Long)
    override fun onDestroy() {
        super.onDestroy()
        countDownTimer?.cancel()
        mediaPlayer?.release()
    }
}