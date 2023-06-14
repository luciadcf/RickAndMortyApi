package es.luciadcf.rickandmorty.ui.domain

abstract class Card {

    abstract fun getId(): Int
    protected open fun getPrice(): Int = 5
}

class Debit(): Card(){
    inner class sadfa(){

    }
    override fun getId(): Int = 1

}

class Credit() : Card() {
    override fun getId(): Int  = 2
    override fun getPrice(): Int = super.getPrice() + 10

}