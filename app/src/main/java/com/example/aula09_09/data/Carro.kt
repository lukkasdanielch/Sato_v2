package com.example.aula09_09.data

import android.net.Uri

data class Carro(
    val nome: String,
    val modelo: String,
    val ano: Int,
    val placa: String,
    val imagemUri: Uri? = null,
    val imagemRes: Int? = null,
    val fotos: MutableList<Any> = mutableListOf()
)