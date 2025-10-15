package com.example.aula09_09.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FotoCarroDao {
    @Query("SELECT * FROM fotos_carro WHERE carroId = :carroId")
    fun getFotos(carroId: Int): Flow<List<FotoCarro>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foto: FotoCarro)
}
