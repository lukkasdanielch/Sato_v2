package com.example.aula09_09

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.aula09_09.data.Carro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela3(
    navController: NavHostController,
    onCarroAdicionado: (Carro) -> Unit
) {
    var nome by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }

    // Uri da imagem selecionada
    var imagemUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current

    // Launcher para escolher imagem
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imagemUri = uri
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Cadastro de Carro") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Preview da imagem
            if (imagemUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(imagemUri),
                    contentDescription = "Imagem do carro",
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Box(
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Nenhuma imagem selecionada")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { launcher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar Imagem")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = ano,
                onValueChange = { ano = it },
                label = { Text("Ano") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = placa,
                onValueChange = { placa = it },
                label = { Text("Placa") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nome.isNotEmpty() && modelo.isNotEmpty() && ano.isNotEmpty() && placa.isNotEmpty() && imagemUri != null) {
                        // Salva o carro
                        onCarroAdicionado(
                            Carro(
                                nome,
                                modelo,
                                ano.toInt(),
                                placa,
                                imagemUri.hashCode() // podemos armazenar a Uri ou usar hashCode para teste
                            )
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar Carro")
            }
        }
    }
}
