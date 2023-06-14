package es.luciadcf.rickandmorty.data.model.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import es.luciadcf.rickandmorty.data.database.constant.CharacterConstant

@Entity(tableName = CharacterConstant.TABLE_NAME)
data class CharacterDbo(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_NAME) val name: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_IMAGE) val image: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_STATUS) val status: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_SPECIES) val species: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_TYPE) val type: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_GENDER) val gender: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_ORIGIN) val origin: String? = null,
    @ColumnInfo(name = CharacterConstant.COLUMN_CHARACTER_LOCATION) val location: String? = null
)