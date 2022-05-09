package com.moises.characters.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterList(characterList: List<CharacterEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(characterList: CharacterEntity)

    @Query("SELECT * FROM characterEntity as c WHERE c.id = :characterId")
    suspend fun getCharacterById(characterId: Int): CharacterEntity

    @Query("DELETE FROM characterEntity")
    suspend fun clearCharacterList()

    @Query("SELECT * FROM characterEntity")
    fun listCharacters(): PagingSource<Int, CharacterEntity>

}