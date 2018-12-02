package com.example.cressida.slidingpuzzleapp.views

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.cressida.slidingpuzzleapp.LoginRegisterActivity
import com.example.cressida.slidingpuzzleapp.R
import com.example.cressida.slidingpuzzleapp.logic.FireAuth

import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var fireAuth = FireAuth(this)

        register_button.setOnClickListener { fireAuth.registerUser(email.text.toString(), password.text.toString(), passwordre.text.toString(), username.text.toString(), PlayActivity()) }

        fireAuth.logout(LoginRegisterActivity())
    }

}
