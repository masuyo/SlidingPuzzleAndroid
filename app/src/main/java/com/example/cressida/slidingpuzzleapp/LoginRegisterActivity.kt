package com.example.cressida.slidingpuzzleapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import kotlinx.android.synthetic.main.activity_login_register.*

class LoginRegisterActivity : Activity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)



        login_button.setOnClickListener { loginUser(email.text.toString(), password.text.toString()) }
        register_button.setOnClickListener { registerUser(email.text.toString(), password.text.toString()) }

        auth = FirebaseAuth.getInstance()
    }

    private fun registerUser(email: String, password: String) {
        if (!validateForm()) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->

            if (task.isSuccessful) {
                setResult(RESULT_OK, Intent())
                finish()
            } else {
                Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val email = email.text.toString()
        if (TextUtils.isEmpty(email)) {
            valid = false
        }

        val password = password.text.toString()
        if (TextUtils.isEmpty(password)) {
            valid = false
        }

        return valid
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                setResult(RESULT_OK, Intent())
                finish()
                Toast.makeText(this, "Login succesful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }


}
