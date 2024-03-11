package com.example.se2einzelphase

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.se2einzelphase.network.sendAndReceiveTCP
import com.example.se2einzelphase.ui.theme.SE2EinzelphaseTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SE2EinzelphaseTheme() {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    AppContent()
                }
            }
        }
    }
}

fun calculateCrossSumAsBinary(number: String): String {
    val sum = number.filter { it.isDigit() }.sumOf { it.toString().toInt() }
    return sum.toString(2) // Convert the sum to a binary string
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent(modifier: Modifier = Modifier) {
    var serverResponse by remember { mutableStateOf<String?>(null) }
    var input by remember { mutableStateOf("") }
    val crossSumBinary = calculateCrossSumAsBinary(input)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text("NetworkTest") }
        )

        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Gib deine Matrikelnummer ein:")

            OutlinedTextField(
                value = input,
                onValueChange = { newInput ->
                    if (newInput.all { it.isDigit() }) input = newInput
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
                    .padding(0.dp, 16.dp)
            )

            Button(onClick = {
                CoroutineScope(Dispatchers.Main).launch {
                   serverResponse = sendAndReceiveTCP(input)
                }

            }) {
                Text("Abschicken")
            }

            Text("Serverantwort: $serverResponse")


            Text("Quersumme (Binary): $crossSumBinary", Modifier.padding(top = 8.dp))
        }
    }
}