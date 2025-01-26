package com.example.relax25

import android.media.Image
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.relax25.R
import kotlinx.coroutines.*

class MusicActivity : AppCompatActivity() {

    private lateinit var playButton: ImageButton
    private lateinit var pauseButton: ImageButton
    private lateinit var stopButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var songTitleTextView: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var currentTextView: TextView
    private lateinit var durationTextView: TextView

    private var mediaPlayer: MediaPlayer? = null
    private var currentSongIndex = 0
    private val songList = listOf(
        Song("песня 1", R.raw.song1),
        Song("песяня 2", R.raw.song2),
        Song("песня 3", R.raw.song3)
    )

    private var isUpdatingSeekBar = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        playButton = findViewById(R.id.playButton)
        pauseButton = findViewById(R.id.pauseButton)
        stopButton = findViewById(R.id.stopButton)
        nextButton = findViewById(R.id.nextButton)
        songTitleTextView = findViewById(R.id.songTitleTextView)
        seekBar = findViewById(R.id.seekBar)
        currentTextView = findViewById(R.id.currentTextView)
        durationTextView = findViewById(R.id.durationTextView)

        playButton.setOnClickListener { playAudio() }
        pauseButton.setOnClickListener { pauseAudio() }
        stopButton.setOnClickListener { stopAudio() }
        nextButton.setOnClickListener { playNextSong() }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                    updateUI()
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        loadSong()
    }

    private fun loadSong() {
        val currentSong = songList[currentSongIndex]
        songTitleTextView.text = currentSong.title

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, currentSong.resourceId)
        seekBar.max = mediaPlayer?.duration ?: 0
        updateUI()
    }
    private fun updateUI() {
        mediaPlayer?.let {
            seekBar.max = it.duration
            durationTextView.text = formatTime(it.duration)
            currentTextView.text = formatTime(it.currentPosition)
            seekBar.progress = it.currentPosition
        }
    }
    private fun startUpdatingSeekBar() {
        if(isUpdatingSeekBar) return;
        isUpdatingSeekBar = true
        CoroutineScope(Dispatchers.IO).launch {
            while (isActive && mediaPlayer?.isPlaying == true) {
                delay(1000) // Задержка в 1 секунду
                withContext(Dispatchers.Main) {
                    updateUI()
                }
            }
            isUpdatingSeekBar = false
        }
    }
    private fun stopUpdatingSeekBar() {
        isUpdatingSeekBar = false
    }
    private fun formatTime(milliseconds: Int): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }


    private fun playAudio() {
        mediaPlayer?.apply {
            if (!isPlaying) {
                start()
                startUpdatingSeekBar()
            }
        }
    }
    private fun pauseAudio() {
        mediaPlayer?.apply {
            if (isPlaying) {
                pause()
                stopUpdatingSeekBar()
            }
        }

    }
    private fun stopAudio() {
        mediaPlayer?.apply {
            stop()
            prepare()
            seekTo(0)
            seekBar.progress = 0
            stopUpdatingSeekBar()
            updateUI()
        }
    }
    private fun playNextSong() {
        currentSongIndex = (currentSongIndex + 1) % songList.size
        loadSong()
        playAudio()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
        stopUpdatingSeekBar()
    }


    private data class Song(val title: String, val resourceId: Int)
}