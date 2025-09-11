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
            Carro("HB20", "MLV3I33", 2020, "ASD6522", R.drawable.hb20),
            Carro("Onix", "KJD8H92", 2021, "BXD9033", R.drawable.celta)
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
            }
        }
    }
}
