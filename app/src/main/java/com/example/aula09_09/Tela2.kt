package com.example.aula09_09

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
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

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun Tela2(nome: String, navController: NavHostController, listaCarros: MutableList<Carro>) {

    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(title = { Text("Gestão de Carros - Bem-vindo, $nome!") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("tela3") },
                containerColor = Color(0xFFAA162C),
                contentColor = Color.White
            ) { Text("+") }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (listaCarros.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum carro cadastrado.", color = Color.White)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(listaCarros) { carro ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("tela4/${carro.placa}")
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(modifier = Modifier.padding(16.dp)) {
                                Column {
                                    Text("Nome: ${carro.nome}", style = MaterialTheme.typography.titleMedium)
                                    Text("Modelo: ${carro.modelo}")
                                    Text("Ano: ${carro.ano}")
                                    Text("Placa: ${carro.placa}")
                                }
                                Spacer(modifier = Modifier.width(50.dp))
                                Image(
                                    painter = when {
                                        carro.imagemUri != null -> rememberAsyncImagePainter(carro.imagemUri) // URI da galeria/câmera
                                        carro.imagemRes != null -> painterResource(id = carro.imagemRes)     // drawable
                                        else -> painterResource(id = R.drawable.hb20)                        // fallback
                                    },
                                    contentDescription = carro.nome,
                                    modifier = Modifier.size(100.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }

                    }
                        }
                    }
                }
            }
        }



@Preview(showBackground = true)
@Composable
fun Tela2Preview() {
    // Lista de carros de exemplo (MutableList)
    val listaCarrosFake: MutableList<Carro> = mutableListOf(
        Carro(
            nome = "Onix",
            modelo = "KJD8H92",
            ano = 2021,
            placa = "BXD9033",
            imagemRes = R.drawable.celta,
            fotos = mutableListOf(R.drawable.celta, R.drawable.gol)
        ),
        Carro(
            nome = "Civic",
            modelo = "HGJ92K",
            ano = 2020,
            placa = "ABC1234",
            imagemRes = R.drawable.civic,
            fotos = mutableListOf(R.drawable.civic)
        )
    )

    Tela2(
        nome = "Lucas",
        navController = rememberNavController(),
        listaCarros = listaCarrosFake
    )
}