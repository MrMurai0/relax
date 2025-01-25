package com.example.relax25

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer

class AlarmReceiver : BroadcastReceiver() {

    private var mediaPlayer: MediaPlayer? = null
    override fun onReceive(context: Context, intent: Intent) {

        mediaPlayer =  MediaPlayer.create(context, R.raw.d)
        mediaPlayer?.start()
    }
}