package com.example.aula09_09

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.aula09_09.data.Carro
import com.example.aula09_09.data.CarroDao
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela4(
    navController: NavHostController,
    carro: Carro,
    carroDao: CarroDao
) {
    val scope = rememberCoroutineScope()

    var nome by remember { mutableStateOf(carro.nome) }
    var modelo by remember { mutableStateOf(carro.modelo) }
    var ano by remember { mutableStateOf(carro.ano.toString()) }
    var placa by remember { mutableStateOf(carro.placa) }
    var imagemUri by remember { mutableStateOf<Uri?>(carro.imagemUri?.let { Uri.parse(it) }) }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imagemUri = it }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("${carro.nome} - ${carro.placa}") },
                navigationIcon = {
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text("< Voltar") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Imagem do carro
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imagemUri?.let { rememberAsyncImagePainter(it) }
                        ?: carro.imagemRes?.let { painterResource(id = it) }
                        ?: painterResource(id = R.drawable.hb20),
                    contentDescription = "Imagem do Carro",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) { Text("Alterar Imagem") }

            OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = modelo, onValueChange = { modelo = it }, label = { Text("Modelo") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = ano, onValueChange = { ano = it }, label = { Text("Ano") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = placa, onValueChange = { placa = it }, label = { Text("Placa") }, modifier = Modifier.fillMaxWidth())

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = {
                        scope.launch {
                            val carroAtualizado = carro.copy(
                                nome = nome,
                                modelo = modelo,
                                ano = ano.toIntOrNull() ?: carro.ano,
                                placa = placa,
                                imagemUri = imagemUri?.toString()
                            )
                            carroDao.update(carroAtualizado)
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) { Text("Salvar") }


            }
        }
    }
}
