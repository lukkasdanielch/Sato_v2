package com.example.aula09_09

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.painterResource
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
    val fotos = remember { mutableStateListOf<Any>().apply { addAll(carro.fotos) } }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            fotos.add(it)
            carro.fotos.add(it)
        }
    }

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = { Text("${carro.nome} - ${carro.placa}") },
                navigationIcon = {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFAA162C),
                            contentColor = Color.White
                        )
                    ) {
                        Text("< Voltar")
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    launcher.launch("image/*")
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
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(fotos) { foto ->
                    Surface(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            painter = when (foto) {
                                is Int -> painterResource(id = foto)
                                is Uri -> rememberAsyncImagePainter(foto)
                                else -> painterResource(id = R.drawable.civic)
                            },
                            contentDescription = "Foto do carro",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Tela4Preview() {
    val carroFake = Carro(
        nome = "Onix",
        modelo = "KJD8H92",
        ano = 2021,
        placa = "BXD9033",
        imagemRes = R.drawable.celta,
        fotos = mutableListOf(
            R.drawable.celta,
            R.drawable.gol,
            R.drawable.civic
        )
    )

    Tela4(
        navController = rememberNavController(),
        carro = carroFake
    )
}