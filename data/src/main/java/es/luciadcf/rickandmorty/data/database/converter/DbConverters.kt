package es.luciadcf.rickandmorty.data.database.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DbConverters {

    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        return Gson().fromJson(value, object : TypeToken<List<String>>() {}.type)
    }

    @TypeConverter
    fun fromListStringToString(values: List<String>): String {
        return Gson().toJson(values)
    }

    @TypeConverter
    fun fromStringToInt(value: String): Int {
        return value.toInt()
    }

    @TypeConverter
    fun fromIntToString(value: Int): String {
        return value.toString()
    }

    @TypeConverter
    fun fromListIntToString(values: List<Int>): String {
        return Gson().toJson(values)
    }

}