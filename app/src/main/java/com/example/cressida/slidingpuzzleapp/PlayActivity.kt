package com.example.cressida.slidingpuzzleapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PlayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        var playB = findViewById<ImageView>(R.id.playButton)
        playB.setOnClickListener {
            val intent = Intent( this, LevelActivity::class.java)
            startActivity(intent)
        }

        var settingsB = findViewById<ImageView>(R.id.settingsButton)
        settingsB.setOnClickListener {
            val intent = Intent( this, SettingsActivity::class.java)
            startActivity(intent)
        }

        var leaderboardB = findViewById<ImageView>(R.id.leaderboardButton)
        leaderboardB.setOnClickListener {
            val intent = Intent( this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }
}
