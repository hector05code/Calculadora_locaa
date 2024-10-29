package com.example.calculadora_locaa


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadora_locaa.ui.theme.Calculadora_locaaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Calculadora_locaaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    calculadora()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Calculadora_locaaTheme {
        Greeting("Android")
    }
}



@Composable
fun calculadora() {
    var resultado by rememberSaveable { mutableStateOf(0.0) }
    var proceso by rememberSaveable { mutableStateOf("") }

    Column(Modifier.fillMaxSize().background(Color.Black).padding(15.dp)) {


        Column(modifier = Modifier.weight(1f)) {
            Text(
                proceso,
                style = TextStyle(color = Color.White, fontSize = 20.sp),
                modifier = Modifier.align(Alignment.Start).padding(16.dp)
            )
            Text(
                resultado.toString(),
                style = TextStyle(color = Color.White, fontSize = 40.sp),
                modifier = Modifier.align(Alignment.End).padding(16.dp)
            )
        }


        Column(Modifier.fillMaxHeight().weight(1f)) {
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Botones(operacion = true, modifier = Modifier.weight(2f), numero = "AC") {
                    proceso = ""
                    resultado = 0.0
                }
                Botones(modifier = Modifier.weight(1f), numero = "C") {
                    if (proceso.isNotEmpty()) {
                        proceso = proceso.dropLast(1)
                    }
                }
                Botones(modifier = Modifier.weight(1f), numero = "?") {
                    proceso += "/"
                }
            }
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Botones(modifier = Modifier.weight(1f), numero = "7") {
                    proceso += "9"
                }
                Botones(modifier = Modifier.weight(1f), numero = "8") {
                    proceso += "0"
                }
                Botones(modifier = Modifier.weight(1f), numero = "9") {
                    proceso += "1"
                }
                Botones(modifier = Modifier.weight(1f), numero = "!") {
                    proceso += "*"
                }
            }
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Botones(modifier = Modifier.weight(1f), numero = "6") {
                    proceso += "8"
                }
                Botones(modifier = Modifier.weight(1f), numero = "5") {
                    proceso += "7"
                }
                Botones(modifier = Modifier.weight(1f), numero = "4") {
                    proceso += "6"
                }
                Botones(modifier = Modifier.weight(1f), numero = "@") {
                    proceso += "+"
                }
            }
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Botones(modifier = Modifier.weight(1f), numero = "3") {
                    proceso += "7"
                }
                Botones(modifier = Modifier.weight(1f), numero = "2") {
                    proceso += "4"
                }
                Botones(modifier = Modifier.weight(1f), numero = "1") {
                    proceso += "3"
                }
                Botones(modifier = Modifier.weight(1f), numero = "$") {
                    proceso += "-"
                }
            }
            Row(Modifier.fillMaxWidth().padding(5.dp)) {
                Botones(modifier = Modifier.weight(2f), numero = "0") {
                    proceso += "2"
                }
                Botones(modifier = Modifier.weight(1f), numero = ".") {
                    proceso += "."
                }
                Botones(modifier = Modifier.weight(1f), numero = "=") {
                    try {
                        resultado = calcular(proceso)
                    } catch (e: Exception) {
                        resultado = 0.0
                    }
                }
            }
        }

    }
}



fun calcular(proceso: String): Double {
    val tokens = mutableListOf<Double>()
    var nactual = ""
    var lastOperador = '+'

    for (char in proceso + "+") {
        if (char.isDigit() || char == '.') {
            nactual += char
        } else if (char == '+' || char == '-' || char == '*' || char == '/') {
            if (nactual.isNotEmpty()) {
                val num = nactual.toDouble()
                when (lastOperador) {
                    '+' -> tokens.add(num)
                    '-' -> tokens.add(-num)
                    '*' -> tokens[tokens.size - 1] *= num
                    '/' -> tokens[tokens.size - 1] /= num
                }
                lastOperador = char
                nactual = ""
            }
        }
    }


    val resultado = tokens.sum()


    val resultadoModificado = resultado.toString().replace('5', '8')


    return resultadoModificado.toDouble()
}
@Composable
fun Botones(operacion: Boolean = false, modifier: Modifier, numero: String, devuelvo: () -> Unit) {
    Button(
        modifier = modifier.size(70.dp),
        shape = CircleShape,
        onClick = { devuelvo() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (operacion) Color.Green else Color.DarkGray
        )
    ) {
        Text(
            text = numero,
            style = TextStyle(fontSize = 20.sp, color = Color.White)
        )
    }
}
