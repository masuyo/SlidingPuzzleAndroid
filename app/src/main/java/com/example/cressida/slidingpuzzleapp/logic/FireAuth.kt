package com.example.cressida.slidingpuzzleapp.logic

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class FireAuth(private val callerAct: Activity) : FireControl() {

    fun isUserLoggedIn(forwardAct: Activity) {
        val user = auth.currentUser
        if (user != null) {
            val intent = Intent(callerAct.applicationContext, forwardAct::class.java)
            callerAct.startActivity(intent)
        }
    }

    private fun CreateUserDocument(username: String) {
        val db = FirebaseFirestore.getInstance()

        var data = HashMap<String, Any>()
        data["username"] = username
        data["highscore"] = 0


        db.collection("users").document(auth.uid!!).set(data)
    }

    fun registerUser(email: String, password: String, passwordRe: String, username: String, forwardAct: Activity) {
        if (!validateForm(username, email, password, passwordRe)) {
            Toast.makeText(callerAct, "Registration failed.", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(callerAct) { task ->
            if (task.isSuccessful) {
                CreateUserDocument(username)
                val intent = Intent(callerAct, forwardAct::class.java)
                callerAct.startActivity(intent)
            } else {
                Toast.makeText(callerAct, "Registration failed.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginUser(email: String, password: String, forwardAct: Activity) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(callerAct) { task ->
            if (task.isSuccessful) {
                val intent = Intent(callerAct, forwardAct::class.java)
                callerAct.startActivity(intent)
                Toast.makeText(callerAct, "Login successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(callerAct, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resetUserPass(email: String) {
        auth.sendPasswordResetEmail(email)
    }

    fun logout(logInActivity: Activity) {
        auth.signOut()
        val login = Intent(callerAct, logInActivity::class.java)
        callerAct.startActivity(login)
    }

    fun getUserEmail(): String? {
        val user = auth.currentUser
        return user?.email
    }

    private fun validateForm(username: String, email: String, password: String, passwordRe: String): Boolean {
        var valid = true

        if (TextUtils.isEmpty(email)) {
            valid = false
        }

        if (TextUtils.isEmpty(password)) {
            valid = false
        }

        if (password != passwordRe) {
            valid = false
        }

        if (TextUtils.isEmpty(username)) {
            valid = false;
        }

        return valid
    }
}