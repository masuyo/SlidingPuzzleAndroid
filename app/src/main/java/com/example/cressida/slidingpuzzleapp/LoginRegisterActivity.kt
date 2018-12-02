package com.example.cressida.slidingpuzzleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.cressida.slidingpuzzleapp.logic.FireAuth

class LoginRegisterActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        var fireAuth = FireAuth(this)

        val intent = Intent(applicationContext, PlayActivity::class.java)
        startActivity(intent)

        //fireAuth.isUserLoggedIn()

        //login_button.setOnClickListener { fireAuth.loginUser(email.text.toString(), password.text.toString()) }
        //register_button.setOnClickListener { fireAuth.registerUser(email.text.toString(), password.text.toString()) }

    }

}
