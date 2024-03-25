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
    var jogadorAtual by remember { mutableStateOf("X") }
    var tabuleiro by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var jogoEmAndamento by remember { mutableStateOf(true) }
    var vencedor by remember { mutableStateOf("") }

    fun verificarVencedor(tabuleiro: Array<Array<String>>): String {
        // Verificar linhas
        for (i in 0 until 3) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0].isNotEmpty()) {
                return tabuleiro[i][0]
            }
        }
        // Verificar colunas
        for (j in 0 until 3) {
            if (tabuleiro[0][j] == tabuleiro[1][j] && tabuleiro[1][j] == tabuleiro[2][j] && tabuleiro[0][j].isNotEmpty()) {
                return tabuleiro[0][j]
            }
        }
        // Verificar diagonal principal
        if (tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0].isNotEmpty()) {
            return tabuleiro[0][0]
        }
        // Verificar diagonal secundária
        if (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0] && tabuleiro[0][2].isNotEmpty()) {
            return tabuleiro[0][2]
        }

        var todasAsCelulasPreenchidas = true
        for (linha in tabuleiro) {
            for (celula in linha) {
                if (celula.isEmpty()) {
                    todasAsCelulasPreenchidas = false
                    break
                }
            }
            if (!todasAsCelulasPreenchidas) {
                break
            }
        }
        if (todasAsCelulasPreenchidas && vencedor.isEmpty()) {
            return "Empate"
        }

        // Se não houver vencedor
        return ""
    }

        Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )

        Column (
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
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
                    if (jogoEmAndamento && tabuleiro[row][col].isEmpty()) {
                        tabuleiro[row][col] = jogadorAtual // Faz a jogada na matriz do tabuleiro
                        jogadorAtual = if (jogadorAtual == "X") "O" else "X" // Alterna o jogador
                        vencedor = verificarVencedor(tabuleiro) // Verifica se há um vencedor após cada jogada
                        if (vencedor.isNotEmpty()) {
                            jogoEmAndamento = false // Define o jogo como não em andamento
                        }
                    }
                },
                tabuleiro = tabuleiro
            )

            JogarDeNovo(
                modifier = modifier,
                onJogarDeNovoClicked = {
                    tabuleiro = Array(3) { Array(3) { "" } } // Reinicia o tabuleiro
                    jogoEmAndamento = true // Define o jogo como em andamento novamente
                    vencedor = "" // Limpa o vencedor
                }
            )

            if (!jogoEmAndamento && vencedor.isNotEmpty()) {
                FimDeJogo(vencedor = vencedor)
            }

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
                        onClick = {onCelulaClicked(i, j)},
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
fun JogarDeNovo(modifier: Modifier = Modifier, onJogarDeNovoClicked: () -> Unit){
    Column (horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "")
        Button(onClick = { onJogarDeNovoClicked() }) {
            Text(text = "Jogar de Novo")
        }

    }
}

@Composable
fun FimDeJogo(vencedor: String) {
    if (vencedor == "Empate") {
        Text(
            text = "Deu Velha! O jogo Empatou.",
            color = Color.Black,
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    } else {
        Text(
            text = "Parabéns, Jogador $vencedor! Você venceu!",
            color = Color.Black,
            fontSize = 26.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JogoDaVelhaKotlinTheme {
        JogoDaVelha()
    }
}