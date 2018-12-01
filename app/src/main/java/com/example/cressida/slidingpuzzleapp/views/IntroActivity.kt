package com.example.cressida.slidingpuzzleapp.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.os.Handler
import android.support.v4.os.HandlerCompat.postDelayed
import android.widget.Button
import com.example.cressida.slidingpuzzleapp.LoginRegisterActivity
import com.example.cressida.slidingpuzzleapp.R
import java.util.*


class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        val startButton = findViewById<Button>(R.id.startbutton)
        startButton.setOnClickListener{
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }
    }
}
