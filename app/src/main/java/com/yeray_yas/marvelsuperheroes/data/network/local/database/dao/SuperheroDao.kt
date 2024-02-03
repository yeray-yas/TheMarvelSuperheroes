package com.yeray_yas.marvelsuperheroes.data.network.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yeray_yas.marvelsuperheroes.data.network.local.database.entities.SuperheroEntity

@Dao
interface SuperheroDao {
    @Query("SELECT * FROM superhero_table ORDER BY results DESC")
    suspend fun getAllSuperheroes(): List<SuperheroEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(superheroes: List<SuperheroEntity>)

}