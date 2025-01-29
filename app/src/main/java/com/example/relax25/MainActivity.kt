package com.example.relax25

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.relax25.R
import com.google.android.material.card.MaterialCardView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButtons()
    }

    private fun setupButtons() {
        val timerButtonCard = findViewById<MaterialCardView>(R.id.timerCard)
        val musicButtonCard = findViewById<MaterialCardView>(R.id.musicCard)
        val yogaButtonCard = findViewById<MaterialCardView>(R.id.yogaCard)
        val notesButtonCard = findViewById<MaterialCardView>(R.id.notesCard)

        timerButtonCard.setOnClickListener {
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        musicButtonCard.setOnClickListener {
            val intent = Intent(this, MusicActivity::class.java)
            startActivity(intent)
        }

        yogaButtonCard.setOnClickListener {
            val intent = Intent(this, YogaActivity::class.java)
            startActivity(intent)
        }
        notesButtonCard.setOnClickListener {
            val intent = Intent(this, notes::class.java)
            startActivity(intent)
        }
    }
}