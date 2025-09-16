package com.example.aula09_09


import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aula09_09.data.Carro

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val listaCarros = remember {
        mutableStateListOf(
            Carro(
                nome = "HB20",
                modelo = "MLV3I33",
                ano = 2020,
                placa = "ASD6522",
                imagemRes = R.drawable.hb2022,
                fotos = mutableListOf(
                    R.drawable.hb2022,   // foto principal
                    R.drawable.hb202,   // pode repetir ou trocar
                )
            ),
            Carro(
                nome = "Sandero",
                modelo = "BNK2010",
                ano = 2021,
                placa = "BNK2010",
                imagemRes = R.drawable.sandero,
                fotos = mutableListOf(
                    R.drawable.sandero1,  // foto principal
                    R.drawable.sandero   // outra foto
                )
            ),
            Carro(
                nome = "celta",
                modelo = "KJD8H92",
                ano = 2021,
                placa = "BXD9033",
                imagemRes = R.drawable.celta,
                fotos = mutableListOf(
                    R.drawable.celta2,  // foto principal
                    R.drawable.celta   // outra foto
                )
            )
        )
    }
    NavHost(navController, startDestination = "tela1") {
        composable("tela1") { Tela1(navController) }
        composable("tela2/{nome}") { backStackEntry ->
            val nome = backStackEntry.arguments?.getString("nome") ?: "sem nome"
            Tela2(nome, navController, listaCarros)
        }
        composable("tela3") {
            Tela3(navController) { carroNovo ->
                listaCarros.add(carroNovo)
                navController.popBackStack() // volta para Tela2
            }
        }
        composable("tela4/{placa}") { backStackEntry ->
            val placa = backStackEntry.arguments?.getString("placa")
            val carro = listaCarros.find { it.placa == placa }
            if (carro != null) {
                Tela4(navController, carro)
            }
        }
    }
}
