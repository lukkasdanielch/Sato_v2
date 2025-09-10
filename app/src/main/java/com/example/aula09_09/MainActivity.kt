package com.example.aula09_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aula09_09.ui.theme.Aula0909Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Aula0909Theme {
                Scaffold { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(10.dp)
                            .fillMaxSize()
                            .background(Color.Black) //
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }

    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "tela1") {

            composable("tela1") {
                Tela1(navController)
            }



            composable("tela2") {
                val no = it.arguments?.getString("nome") ?: "sem nome"
                Tela2(no, navController)
            }

        }
    }

    @Composable
    fun Tela1(navController: NavHostController) {


        Card(
            colors = CardDefaults.cardColors(Color.Black),
            modifier = Modifier.fillMaxSize().height(180.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize()
                    .padding(18.dp)
            ) {


                var usuario by remember { mutableStateOf("") }
                var senha by remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.sato),
                        contentDescription = "Logo do app",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(top = 16.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    TextField(

                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("usuario") },
                        modifier = Modifier.fillMaxWidth(),


                        )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = senha,
                        onValueChange = { senha = it },
                        label = { Text("Senha") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = PasswordVisualTransformation()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("tela2") },
                        colors = ButtonDefaults.buttonColors(Color.Red),
                    ) {


                        Text("Entrar")
                    }

                }
            }
        }
    }
}

    @Composable
    fun Tela2(nome: String, navController: NavHostController) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Campo de pesquisa
            Pesquisar()

            Spacer(modifier = Modifier.height(16.dp))

            // Grid de cards
            Box(modifier = Modifier.weight(1f)) { // ocupa o espaço disponível
                GridDeCards()
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.popBackStack()
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E88E5),
                    contentColor = Color.White
                )
            ) {
                Text("+ Adicionar Carro")
            }

        }



    }

    @Composable
    fun Tela3(onClickAvancar: () -> Unit) {
        val nome = "lucas"
        Button(onClick = onClickAvancar) {
            Text("abrir tela 02")
        }
    }


    @Composable
    fun Pesquisar() {
        var pesquisar by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxWidth() // 👈 só largura, não altura total
                .padding(16.dp),
        ) {
            TextField(
                value = pesquisar,
                onValueChange = { pesquisar = it },
                label = { Text("Pesquisar") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }

    data class Carro(val nome: String, val placa: String, val imagem: Int)


    @Composable
    fun GridDeCards() {
        val listaCarros = listOf(
            Carro("Sandero", "MLV3I33", R.drawable.sandero),
            Carro("HB20", "ABC1234", R.drawable.hb20),
            Carro("Gol", "XYZ9876", R.drawable.gol),
            Carro("Civic", "HJK4567", R.drawable.civic),
            Carro("Lancer", "HJK4567", R.drawable.lancer),
            Carro("Celta", "XYZ9876", R.drawable.celta),
            Carro("320", "HJK4567", R.drawable.bmw),
            Carro("Uno", "XYZ9876", R.drawable.uno),


            )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3), //
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                BotaoAdicionar()
            }
            items(listaCarros) { carro ->
                CardCarro(carro)
            }

        }
    }

    @Composable
    fun CardCarro(carro: Carro) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = carro.imagem),
                    contentDescription = carro.nome,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(carro.nome)
                Text(carro.placa, style = MaterialTheme.typography.bodySmall)
            }
        }
    }

    @Composable
    fun BotaoAdicionar() {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            elevation = CardDefaults.cardElevation(6.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = { /* ação do botão */ },
                    modifier = Modifier

                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1E88E5),
                        contentColor = Color.White
                    )
                ) {
                    Text("+")
                }
            }
        }
    }




