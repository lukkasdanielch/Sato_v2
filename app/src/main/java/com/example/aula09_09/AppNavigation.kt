package com.example.aula09_09

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aula09_09.data.Carro
import androidx.compose.runtime.rememberCoroutineScope
import com.example.aula09_09.data.AppDatabase
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    val db = remember { AppDatabase.getDatabase(context) }
    val carroDao = db.carroDao()
    val usuarioDao = db.usuarioDao()

    val listaCarros by carroDao.getCarros().collectAsState(initial = emptyList())

    NavHost(navController = navController, startDestination = "tela1") {
        composable("tela1") {
            Tela1(navController, usuarioDao)
        }

        composable("tela2/{nome}") { backStackEntry ->
            val nome = backStackEntry.arguments?.getString("nome") ?: "sem nome"
            Tela2(nome = nome, navController = navController, carroDao = carroDao)
        }
        composable("tela3") {
            Tela3(navController = navController, carroDao = carroDao)
        }

        composable("tela4/{placa}") { backStackEntry ->
            val placa = backStackEntry.arguments?.getString("placa")
            val carro = listaCarros.find { it.placa == placa }
            if (carro != null) {
                Tela4(navController = navController, carro = carro, carroDao = carroDao)
            } else {
                navController.popBackStack()
            }

            composable("cadastro_usuario") {
                CadastroUsuario(navController = navController, usuarioDao = db.usuarioDao())
            }
        }
    }
}
