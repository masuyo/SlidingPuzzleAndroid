package com.example.cressida.slidingpuzzleapp.logic

import com.google.firebase.firestore.FirebaseFirestore

class FireDB : FireControl() {

    private val db = FirebaseFirestore.getInstance()
    private val uid = GetUID()

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

        db.collection("users").document(uid).collection("levelscores").document(level).set(data)

        UpdateHighScore()
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

    fun GetAllHighScore(): ArrayList<Pair<String, Double>> {
        val uCollection = db.collection("users")
        var highScoreList = ArrayList<Pair<String, Double>>()

        uCollection.orderBy("highscore").get()
                .addOnSuccessListener {
                    for (document in it) {
                        val username = document.getString("username")!!
                        val highscore = document.getDouble("highscore")!!

                        val pair = Pair<String, Double>(username, highscore)
                        highScoreList.add(pair)
                    }
                }

        return highScoreList
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