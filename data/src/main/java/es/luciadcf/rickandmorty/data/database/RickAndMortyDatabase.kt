package es.luciadcf.rickandmorty.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import es.luciadcf.rickandmorty.data.database.constant.GeneralConstant.DATABASE_NAME
import es.luciadcf.rickandmorty.data.database.converter.DbConverters
import es.luciadcf.rickandmorty.data.database.dao.CharacterDao
import es.luciadcf.rickandmorty.data.model.dbo.CharacterDbo

@Database(
    entities = [
        CharacterDbo::class
    ],
    version = 1
)
@TypeConverters(DbConverters::class)
abstract class RickAndMortyDatabase : RoomDatabase() {

    companion object {
        fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RickAndMortyDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }


    abstract fun getCharacterDao(): CharacterDao

}