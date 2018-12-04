package com.example.cressida.slidingpuzzleapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.cressida.slidingpuzzleapp.logic.FireAuth
import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {

    val auth = FireAuth(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        var playB = findViewById<ImageView>(R.id.playButton)
        playB.setOnClickListener {
            val intent = Intent( this, LevelSelectActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {

        auth.logout(LoginActivity())
    }
}