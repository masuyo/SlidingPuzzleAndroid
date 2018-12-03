package com.example.cressida.slidingpuzzleapp.logic

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.cressida.slidingpuzzleapp.Model.LeaderboardData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FireDB : FireControl() {

    private val db = FirebaseFirestore.getInstance()
    private val uid = GetUID()

    companion object {

        var highScoreList = ArrayList<LeaderboardData>()

    }

    private fun GetUID(): String {

        if (auth.uid == null) {
            throw Exception()
        } else {
            return (auth.uid as String)
        }

    }

    fun UploadScore(level: String, score: Int) {
        val data = HashMap<String, Any>()
        data["score"] = score

        db.collection("users").document(uid).collection("levelscores").document(level).set(data).addOnSuccessListener {
            UpdateHighScore()
        }
    }

    fun GetScore(level: String): Double {
        var score: Double
        score = 0.toDouble()

        db.collection("users").document(uid).collection("levelscores").document(level)
                .get()
                .addOnSuccessListener {
                    score = it.getDouble("score")!!
                }

        return score
    }

    fun GetAllScore(): ArrayList<Pair<String, Double>> {
        var data = ArrayList<Pair<String, Double>>()

        db.collection("users").document(uid).collection("levelscores").get()
                .addOnSuccessListener {
                    for (document in it) {
                        var docID = document.id
                        var docScore = document.getDouble("score")!!
                        var docPair = Pair<String, Double>(docID, docScore)

                        data.add(docPair)
                    }
                }


        return data

    }

    fun GetAllHighScore(adapter: LeaderboardAdapter) {
        val uCollection = db.collection("users")

        highScoreList.clear()

        uCollection.orderBy("highscore", Query.Direction.DESCENDING).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.getResult()!!) {

                            val data = document.toObject(LeaderboardData::class.java)

                            highScoreList.add(document.toObject(LeaderboardData::class.java))
                            //Log.e("sajt", highScoreList.count().toString())
                        }
                        adapter.notifyDataSetChanged()
                    }

                }

        Log.e("sajt", highScoreList.count().toString())
        for (data in highScoreList) {
            Log.e("sajt", data.username + " " + data.highscore)
        }
    }

    fun GetHighScore(): Double {
        var highScore: Double
        highScore = 0.toDouble()

        db.collection("users").document(uid).get()
                .addOnSuccessListener {
                    highScore = it.getDouble("highscore")!!
                }

        return highScore
    }

    private fun UpdateHighScore() {
        val uDocument = db.collection("users").document("uid")
        val uScores = uDocument.collection("levelscores")
        var newScore: Double
        var scores = ArrayList<Double>()

        uScores.get()
                .addOnSuccessListener {
                    for (document in it) {
                        scores.add(document.getDouble("score")!!)
                    }
                }

        newScore = scores.sum()

        uDocument.update("highscore", newScore)
    }
}