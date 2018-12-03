package com.example.cressida.slidingpuzzleapp.views

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.cressida.slidingpuzzleapp.R
import com.example.cressida.slidingpuzzleapp.logic.FireAuth

import kotlinx.android.synthetic.main.activity_login_register.*

class LoginActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        var fireAuth = FireAuth(this)

        fireAuth.isUserLoggedIn(PlayActivity())

        login_button.setOnClickListener { fireAuth.loginUser(email.text.toString(), password.text.toString(), PlayActivity()) }
        register_button.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

}
