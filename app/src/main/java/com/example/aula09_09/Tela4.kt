package com.example.aula09_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.aula09_09.data.Carro
import com.example.aula09_09.ui.theme.Aula0909Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela4(navController: NavHostController, carro: Carro) {
    // Lista de imagens do carro
    var fotos = remember { mutableStateListOf<Any>().apply { addAll(carro.fotos) } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fotos de ${carro.nome}") },
                navigationIcon = {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("< Voltar")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Exemplo: adiciona foto fixa
                    fotos.add(R.drawable.hb20)
                    // 👉 depois você pode trocar para seletor de imagem (gallery/camera)
                },
                containerColor = Color(0xFFAA162C),
                contentColor = Color.White
            ) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Fotos do ${carro.nome}", style = MaterialTheme.typography.titleMedium)

            val fotos = remember { carro.fotos }

            LazyColumn {
                items(fotos) { foto ->
                    Image(
                        painter = rememberAsyncImagePainter(foto),
                        contentDescription = "Foto do carro",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Button(onClick = { fotos.add(R.drawable.hb20) }) {
                Text("Adicionar Foto")
            }
                }
            }
        }


@Preview(showBackground = true)
@Composable
fun Tela4Preview() {
    val carroFake = Carro(
        nome = "HB20",
        modelo = "Comfort",
        ano = 2020,
        placa = "ASD6522",

    )

    Tela4(
        navController = rememberNavController(),
        carro = carroFake
    )
}