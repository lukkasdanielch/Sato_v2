package com.example.aula09_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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



            composable("tela2/{nome}") {
                val no = it.arguments?.getString("nome")?:"sem nome"
                Tela2(no, navController)
            }

        }
    }

    @Composable
    fun Tela1(navController: NavHostController) {

        Button(onClick = {navController.navigate("tela2")}) {
            Text("abrir tela 02")
        }
    }

    @Composable
    fun Tela2(nome: String,navController: NavHostController) {
        Text (text = "Ola $nome!")
        Button(onClick = { navController.popBackStack()
        }) {
            Text("voltar para tela 01")
        }
    }
    @Composable
    fun Tela3(onClickAvancar: () -> Unit) {
        val nome = "lucas"
        Button(onClick = onClickAvancar) {
            Text("abrir tela 02")
        }
    }
}



