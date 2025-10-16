package com.example.aula09_09

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.aula09_09.data.Carro
import com.example.aula09_09.data.CarroDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela2(
    nome: String,
    navController: NavController,
    carroDao: CarroDao
) {
    // Observa a lista de carros do banco
    val listaCarros by carroDao.getCarros().collectAsState(initial = emptyList())
    val scope = CoroutineScope(Dispatchers.IO)

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (listaCarros.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
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
                                .clickable { navController.navigate("tela4/${carro.placa}") },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text("Nome: ${carro.nome}", style = MaterialTheme.typography.titleMedium)
                                    Text("Modelo: ${carro.modelo}")
                                    Text("Ano: ${carro.ano}")
                                    Text("Placa: ${carro.placa}")
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                // Imagem do carro
                                Image(
                                    painter = carro.imagemUri?.let { rememberAsyncImagePainter(it) }
                                        ?: carro.imagemRes?.let { painterResource(id = it) }
                                        ?: painterResource(id = R.drawable.hb20),
                                    contentDescription = carro.nome,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clickable { navController.navigate("tela4/${carro.placa}") },
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                // Botão deletar
                                Button(
                                    onClick = {
                                        scope.launch {
                                            carroDao.delete(carro)
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red,
                                        contentColor = Color.White
                                    )
                                ) { Text("Deletar") }
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
    val carrosFake = listOf(
        Carro(
            nome = "Onix",
            modelo = "LTZ",
            ano = 2022,
            placa = "ABC1234",
            imagemRes = R.drawable.celta
        ),
        Carro(
            nome = "Gol",
            modelo = "1.6",
            ano = 2021,
            placa = "DEF5678",
            imagemRes = R.drawable.gol
        ),
        Carro(
            nome = "Civic",
            modelo = "EX",
            ano = 2020,
            placa = "GHI9012",
            imagemRes = R.drawable.civic
        )
    )

    val navController = rememberNavController()
    Tela2PreviewHelper(nome = "Lucas", navController = navController, listaCarros = carrosFake)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela2PreviewHelper(nome: String, navController: NavController, listaCarros: List<Carro>) {
    Scaffold(
        containerColor = Color.Black,
        topBar = {
            TopAppBar(title = { Text("Gestão de Carros - Bem-vindo, $nome!") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* não faz nada no preview */ },
                containerColor = Color(0xFFAA162C),
                contentColor = Color.White
            ) { Text("+") }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaCarros) { carro ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Nome: ${carro.nome}", style = MaterialTheme.typography.titleMedium)
                                Text("Modelo: ${carro.modelo}")
                                Text("Ano: ${carro.ano}")
                                Text("Placa: ${carro.placa}")
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Image(
                                painter = carro.imagemRes?.let { painterResource(id = it) }
                                    ?: painterResource(id = R.drawable.hb20),
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
