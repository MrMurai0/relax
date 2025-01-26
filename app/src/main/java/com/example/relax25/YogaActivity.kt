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
        Workout(R.drawable.ffgh, "Поза 1", "Поза горы\n" +
                "\n" +
                "Асана выполняется в положении стоя, когда ноги и стопы расположены вместе, на них равномерно распределен вес тела, руки выпрямлены и свободно опущены. На вдохе руки нужно поднять, направив ладони внутрь, и потянуться к небу кончиками пальцев.", 60000),
        Workout(R.drawable.fgd, "Поза 2", "Воин I (Вирабхадрасана I)\n" +
                "\n" +
                "Для ее выполнения встаньте в позу горы, затем сделайте шаг вперед правой ногой, согнув колено до прямого угла. Пятка левой ноги должна быть поднята, а колено направлено в сторону. Вытяните руки вверх над головой или направьте их в разные стороны, ладони обращены друг к другу или смотрят вниз.", 60000),
        Workout(R.drawable.h, "Поза 3", "Наклон вперед стоя (Уттанасана)\n" +
                "\n" +
                "Чтобы выполнить эту асану, нужно встать прямо, ноги поставить на ширине плеч. Затем медленно наклонитесь вперед, стараясь дотянуться руками до пола или обхватить голени. Важно сохранять спину прямой и не сгибать колени.", 60000)

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
            mediaPlayer = MediaPlayer.create(this, R.raw.asian)
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