package com.example.aula09_09.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fotos_carro")
data class FotoCarro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carroId: Int,
    val imagemRes: Int
)
