package com.example.jogodavelhakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jogodavelhakotlin.ui.theme.JogoDaVelhaKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JogoDaVelhaKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JogoDaVelha()
                }
            }
        }
    }
}

@Composable
fun JogoDaVelha(modifier: Modifier = Modifier) {
    TelaPrincipal()
}

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier){
    var jogadorAtual by remember { mutableStateOf("X") } // Estado para controlar o jogador atual
    var tabuleiro1 by remember { mutableStateOf(Array(3) { Array(3) { "" } }) } // Estado para representar o tabuleiro

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )

        Column (
            modifier = modifier.fillMaxWidth().fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            VezJogador(
                jogador = jogadorAtual,
                modifier = modifier
            )

            Tabuleiro(
                modifier = modifier,
                onCelulaClicked = { row, col ->
                    if (tabuleiro1[row][col].isEmpty()) {
                        tabuleiro1[row][col] = jogadorAtual // Faz a jogada na matriz do tabuleiro
                        jogadorAtual = if (jogadorAtual == "X") "O" else "X" // Alterna o jogador
                    }
                },
                tabuleiro = tabuleiro1
            )

            JogarDeNovo(
                modifier = modifier
            )

        }
    }
}

@Composable
fun VezJogador(jogador: String, modifier: Modifier = Modifier){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Text(text = "Vez do Jogador: $jogador", color = Color.Black, fontSize = 26.sp)
    }
}
@Composable
fun Tabuleiro(modifier: Modifier = Modifier,
              onCelulaClicked: (row: Int, col: Int) -> Unit,
              tabuleiro: Array<Array<String>>) {

    Column(modifier = modifier) {
        for (i in tabuleiro.indices) {
            Row {
                for (j in tabuleiro[i].indices) {
                    Celula(
                        text = tabuleiro[i][j],
                        onClick = { onCelulaClicked(i, j) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun Celula(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(4.dp)
            .aspectRatio(1f),
        shape = RoundedCornerShape(0.dp),


    ) {
        Text(text = text, fontSize = 50.sp, color = Color.White)
    }
}


@Composable
fun JogarDeNovo(modifier: Modifier = Modifier){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "")
        Button(onClick = { }) {
            Text(text = "Jogar de Novo")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JogoDaVelhaKotlinTheme {
        JogoDaVelha()
    }
}