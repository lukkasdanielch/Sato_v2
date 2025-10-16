package com.example.aula09_09

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.aula09_09.data.Carro
import com.example.aula09_09.data.CarroDao
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Tela3(navController: NavHostController, carroDao: CarroDao) {
    var nome by remember { mutableStateOf("") }
    var modelo by remember { mutableStateOf("") }
    var ano by remember { mutableStateOf("") }
    var placa by remember { mutableStateOf("") }
    var imagemUri by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { imagemUri = it.toString() }
    }

    Scaffold(
        containerColor = Color.Black,
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
            Button(
                onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFAA162C),
                    contentColor = Color.White
                ),
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
            OutlinedTextField(
                value = modelo,
                onValueChange = { modelo = it },
                label = { Text("Modelo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = ano,
                onValueChange = { ano = it },
                label = { Text("Ano") },
                modifier = Modifier.fillMaxWidth()
            )
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

                        // Salva a imagem na pasta interna
                        val caminhoImagem = saveImageToInternalStorage(
                            context,
                            Uri.parse(imagemUri),
                            "carro_${placa}.jpg"
                        )

                        val novoCarro = Carro(
                            nome = nome,
                            modelo = modelo,
                            ano = ano.toIntOrNull() ?: 0,
                            placa = placa,
                            imagemUri = caminhoImagem
                        )

                        scope.launch {
                            carroDao.insert(novoCarro)
                            // limpa os campos após salvar
                            nome = ""
                            modelo = ""
                            ano = ""
                            placa = ""
                            imagemUri = null

                            navController.popBackStack()
                        }
                    } else {
                        // Mostra um Toast se algum campo estiver vazio ou se a imagem não estiver selecionada
                        Toast.makeText(
                            context,
                            "Preencha todos os campos e adicione uma imagem!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFAA162C),
                    contentColor = Color.White
                )
            ) {
                Text("Salvar Carro")
            }
        }
    }
}

// Função para salvar imagem na pasta interna
fun saveImageToInternalStorage(context: Context, uri: Uri, fileName: String): String {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, fileName)
    inputStream.use { input ->
        file.outputStream().use { output ->
            input?.copyTo(output)
        }
    }
    return file.absolutePath
}