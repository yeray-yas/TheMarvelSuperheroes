package com.yeray_yas.marvelsuperheroes.data.network.local.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yeray_yas.marvelsuperheroes.domain.model.CharacterData
import com.yeray_yas.marvelsuperheroes.domain.model.Data

@Entity(tableName = "superhero_table")
data class SuperheroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "data") val data: Data,
    @ColumnInfo(name = "results") val results: List<CharacterData>

)
