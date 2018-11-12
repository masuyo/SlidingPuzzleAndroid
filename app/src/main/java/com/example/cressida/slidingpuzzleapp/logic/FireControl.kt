package com.example.cressida.slidingpuzzleapp.logic

import com.google.firebase.auth.FirebaseAuth

abstract class FireControl {

    protected lateinit var auth: FirebaseAuth

    constructor() {
        auth = FirebaseAuth.getInstance()
    }


}