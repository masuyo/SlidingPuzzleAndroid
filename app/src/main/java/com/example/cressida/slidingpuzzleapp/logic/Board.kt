package com.example.cressida.slidingpuzzleapp.logic

import android.content.Context.MODE_PRIVATE
import kotlin.properties.Delegates

class Board {

  /*  var IsEnded: Boolean by Delegates.observable(false) { _, oldValue, newValue ->
        onGameEnding?.invoke(oldValue, newValue)
    }
    var onGameEnding: ((Boolean, Boolean) -> Unit)? = null //akkor is jelez amikor false-ra vissza állítjuk
*/

    //private var size: Int = 0
    var minStep: Int =0
    var actualStep: Int = 0
    //private var minedge: Int = 0
    //private var maxedge: Int = 0
    var table: ArrayList<com.example.cressida.slidingpuzzleapp.logic.Block> = ArrayList()
    private val pruposex = 5
    //private var target: com.example.cressida.slidingpuzzleapp.logic.Block = com.example.cressida.slidingpuzzleapp.logic.Block()

    fun loadMap(mapstring: String)
    {
        //   IsEnded = false
         var map = mapstring.split(',')
        try {
            minStep = map[0].toInt()
            for (i in 1 until  map.size)
            {
                var block = map[i].split(' ')
               table.add( Block(
                        block[0].toInt(),
                        block[1].toInt(),
                        block[2].toInt() ,
                        block[3] == "true"
                ))
            }
        }
        catch (nfe: NumberFormatException){

        }
        catch (oii: IndexOutOfBoundsException){

        }
    }

    fun mozgat(block: Block,step: Int):Boolean
    {
        if (block.vertical){
            block.y += step
        }else{
            block.x += step
        }
        actualStep++
        if ( table[0].x == pruposex-1)
            return true
        return false
    }
    /*
    fun setSize(Size:Int )
    {
        size = Size
        sizerect = Size/6
        for ( i in 0 until table.size )
        {
            table[i].cordinateX = table[i].x * sizerect
            table[i].cordinateY = table[i].y * sizerect
        }
    }
    private fun isEnded()
    {
        if (finiser.x + size -1 ==pruposex)
        {
            IsEnded = false
        }
    }
    private  fun elementontablepoint(x: Int,y: Int): com.example.cressida.slidingpuzzleapp.logic.Block
    {
        for ( i in 0 until table.size )
        {
            if (!table[i].vertical && table[i].x == x)
            {
                if (table[i].y <= y && y < table[i].y+table[i].size)
                    return table[i]
            }
            if (table[i].vertical && table[i].y == y)
            {
                if (table[i].x <= x && x < table[i].x+table[i].size)
                    return table[i]
            }
        }
        return com.example.cressida.slidingpuzzleapp.logic.Block()
    }
    private fun elementInCordinate(x:Int,y:Int) : com.example.cressida.slidingpuzzleapp.logic.Block {
        val xp = x/sizerect
        val xy = y/sizerect
        return elementontablepoint(xp,xy)
    }
    //frissíteni kell utána a megjelenitést,   ha elengedjük a képernyöt ezt kell meghívni, hogy elengedjük az aktuális elem mozgatását.
    fun finishTheMove() {
        if (target.size != -1)
        {
            target.x= target.cordinateX/sizerect //pontositani ha van már alap megjelenités
            target.y = target.cordinateY/sizerect
            target= com.example.cressida.slidingpuzzleapp.logic.Block()
            actualStep++
            //megjelenités modositása
            isEnded()
        }
    }
    //frissíteni a megjelenést, a kordináták a tábla sarkához képest értendők (bal felső a 0,0)
    fun move(X: Int,Y: Int,DistanceX: Int,DistanceY: Int) {
        if (target.size == -1){//mozgatandó elem kiválasztása mozgatás határainak kiszámitása
            target = elementInCordinate(X,Y)
            if (target.size != -1){
                if (target.vertical) {//y irányú ág
                    var i = target.y-1
                    var found = false
                    while (  i >= 0  && !found){//amig nem érjük el az akadályozó pontot
                        if ( elementontablepoint(target.x,i).size!=-1)//ha üres a mező
                        {
                            i--
                        }else{
                            found = true
                        }
                    }
                    minedge = (i+1) * sizerect
                    i = target.y + target.size
                    found = false
                    while (  i < 6  && !found)
                    {
                        if (elementontablepoint(target.x,i).size!=-1) {//üres
                            i++
                        }
                        else{
                            found = true
                        }
                    }
                    maxedge = i * sizerect
                }
                if (target.vertical) {//x irányú ág
                    var i = target.x-1
                    var found = false
                    while (  i >= 0  && !found){//amig nem érjük el az akadályozó pontot
                        if ( elementontablepoint(i,target.y).size!=-1)//ha üres a mező
                        {
                            i--
                        }else{
                            found = true
                        }
                    }
                    minedge = (i+1) * sizerect //kivitele hogy csak akkor számoljuk ha kell
                    i = target.x + target.size
                    found = false
                    while (  i < 6  && !found)
                    {
                        if (elementontablepoint(i,target.y).size!=-1) {//üres
                            i++
                        }
                        else{
                            found = true
                        }
                    }
                    maxedge = i * sizerect
                }
            }
        }
        if (target.size != -1){//mozgatás
            if (target.vertical) {//y irányú ág
                val newy = target.cordinateY + DistanceY
                if ( DistanceY > 0){//lefele mozog méret
                    if (maxedge > newy + target.size*sizerect){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateY = newy
                    }else{
                        target.cordinateY = maxedge - target.size*sizerect
                    }
                }else{//felfele mozog
                    if (minedge < newy){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateY = newy
                    }else{
                        target.cordinateY = minedge
                    }
                }
            }else{//x irányú ág
                val newx = target.cordinateX + DistanceX
                if ( DistanceX > 0){//jobbra mozog
                    if (maxedge > newx + target.size*sizerect){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateX = newx
                    }else{
                        target.cordinateX = maxedge - target.size*sizerect
                    }
                }else{//felfele mozog
                    if (minedge > newx){//nem megy túl az útolsó szabad pozición ameddig elcsuszhat
                        target.cordinateX = newx
                    }else{
                        target.cordinateX = minedge
                    }
                }
            }
        }
    }*/
}