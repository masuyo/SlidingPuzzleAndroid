package com.example.cressida.slidingpuzzleapp.logic

import android.view.View.X
import android.view.View.Y

class Block {

    var cordinateX = 0 //konstruktorbe megjeleni
    var cordinatey = 0 //konstuktorba megjeleni
    var x:Int = 0
    var y: Int = 0
    var size:Int = -1
    var vertical:Boolean = false

    constructor(X:Int,Y:Int,Size:Int,Vertical:Boolean)
    {
        x=X
        y=Y
        size = Size
        vertical= Vertical
    }
    constructor()

}