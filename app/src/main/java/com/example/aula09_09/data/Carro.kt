package com.example.aula09_09.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carros")
data class Carro(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // id automático
    val nome: String,
    val modelo: String,
    val ano: Int,
    val placa: String,
    val imagemRes: Int? = null,

    val imagemUri: String? = null
)