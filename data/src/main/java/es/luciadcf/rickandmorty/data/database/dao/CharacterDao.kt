package es.luciadcf.rickandmorty.data.database.dao

import androidx.room.*
import es.luciadcf.rickandmorty.data.database.constant.CharacterConstant
import es.luciadcf.rickandmorty.data.database.constant.GeneralConstant
import es.luciadcf.rickandmorty.data.model.dbo.CharacterDbo

@Dao
interface CharacterDao {

    @Query("SELECT * FROM ${CharacterConstant.TABLE_NAME}")
    fun getAll(): List<CharacterDbo>

    @Query("SELECT * FROM ${CharacterConstant.TABLE_NAME} WHERE ${GeneralConstant.GENERAL_ID} LIKE :id")
    fun getById(id: Int): CharacterDbo

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(character: CharacterDbo)

    @Transaction
    fun insert(characters: List<CharacterDbo>) {
        characters.forEach {
            insert(it)
        }
    }

    @Delete
    fun remove(character: CharacterDbo)

}