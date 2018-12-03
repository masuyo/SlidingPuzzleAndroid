package com.example.cressida.slidingpuzzleapp.views

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.cressida.slidingpuzzleapp.MainActivity
import com.example.cressida.slidingpuzzleapp.R
import com.example.cressida.slidingpuzzleapp.SettingsActivity
import com.example.cressida.slidingpuzzleapp.logic.FireAuth
import kotlinx.android.synthetic.main.activity_play.*

class PlayActivity : AppCompatActivity() {

    val auth = FireAuth(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        playButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
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
