package com.yeray_yas.marvelsuperheroes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yeray_yas.marvelsuperheroes.data.network.local.database.dao.SuperheroDao
import com.yeray_yas.marvelsuperheroes.data.network.local.database.entities.SuperheroEntity

@Database(entities = [SuperheroEntity::class], version = 1)
abstract class SuperheroDatabase : RoomDatabase() {

    abstract fun getSuperheroDao(): SuperheroDao
}