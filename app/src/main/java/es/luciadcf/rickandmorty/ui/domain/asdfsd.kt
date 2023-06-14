package es.luciadcf.rickandmorty.ui.domain

class asdfsd {

    fun solution(S: String): Int {
        val length = S.length
        val substrings = arrayListOf<String>()
        // Implement your solution here
        for(i in 0..length){
            for(j in 0..length){
                substrings.add(S.substring(i, j))
            }
        }
        // ya tenemos todos los substrings
        val substringsRemovedDuplicated = substrings.distinct()
        val shortest = substringsRemovedDuplicated.minBy { it.length }
        return shortest.length
    }

}