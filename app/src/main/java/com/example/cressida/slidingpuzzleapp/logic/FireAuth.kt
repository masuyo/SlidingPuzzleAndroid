package com.example.cressida.slidingpuzzleapp.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast

class FireAuth(private val activity: Activity) : FireControl() {

    fun isUserLoggedIn() {
        val user = auth.currentUser
        if (user != null) {
            activity.setResult(Activity.RESULT_OK, Intent())
            activity.finish()
        }
    }

    fun registerUser(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->

            if (task.isSuccessful) {
                activity.setResult(Activity.RESULT_OK, Intent())
                activity.finish()
            } else {
                Toast.makeText(activity, "Registration failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                activity.setResult(Activity.RESULT_OK, Intent())
                activity.finish()
                Toast.makeText(activity, "Login succesful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetUserPass(email: String) {
        auth.sendPasswordResetEmail(email)
    }

    fun logout(currentAcvitivy: Activity, logInActivity: Activity) {
        auth.signOut()
        val login = Intent(currentAcvitivy, logInActivity::class.java)
        startActivityForResult(currentAcvitivy, login, 0, null)
    }

    fun getUserEmail(): String? {
        val user = auth.currentUser
        return user?.email
    }

    private fun validateForm(email: String, password: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(email)) {
            valid = false
        }

        if (TextUtils.isEmpty(password)) {
            valid = false
        }

        return valid
    }
}