package com.example.cressida.slidingpuzzleapp.logic

class Block {

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