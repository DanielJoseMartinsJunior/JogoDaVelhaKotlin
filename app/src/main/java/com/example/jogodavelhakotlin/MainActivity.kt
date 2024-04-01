package com.example.jogodavelhakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jogodavelhakotlin.ui.theme.JogoDaVelhaKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JogoDaVelhaKotlinTheme {
                // Superfície principal com o jogo da velha
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
fun JogoDaVelha(modifier: Modifier = Modifier){
    // Estado do jogo
    var jogadorAtual by remember { mutableStateOf("X") }
    var tabuleiro by remember { mutableStateOf(Array(3) { Array(3) { "" } }) }
    var jogoEmAndamento by remember { mutableStateOf(true) }
    var vencedor by remember { mutableStateOf("") }

    // Função para verificar o vencedor
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

        // Verificar empate
        var todasAsCelulasPreenchidas = false
        for (linha in tabuleiro) {
            for (celula in linha) {
                if (celula.isEmpty()) {
                    todasAsCelulasPreenchidas = false
                    break
                } else {
                    todasAsCelulasPreenchidas = true
                }
            }
            if (!todasAsCelulasPreenchidas) {
                break
            }
        }
        if (todasAsCelulasPreenchidas) {
            return "Empate"
        }

        // Se não houver vencedor
        return ""
    }

    // Layout da tela principal do jogo
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
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            // Exibir o jogador atual
            VezJogador(
                jogador = jogadorAtual,
                modifier = modifier
            )

            // Renderizar o tabuleiro do jogo
            Tabuleiro(
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

            // Exibir mensagem de fim de jogo
            if (!jogoEmAndamento && vencedor.isNotEmpty()) {
                FimDeJogo(vencedor = vencedor)
            }else{
                Text(text = "\n",
                    fontSize = 25.sp,)
            }

            // Botão para jogar novamente
            JogarDeNovo(
                onJogarDeNovoClicked = {
                    tabuleiro = Array(3) { Array(3) { "" } } // Reinicia o tabuleiro
                    jogoEmAndamento = true // Define o jogo como em andamento novamente
                    vencedor = "" // Limpa o vencedor
                }
            )
        }
    }
}

// Componível para exibir o jogador atual
@Composable
fun VezJogador(jogador: String, modifier: Modifier = Modifier){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ){
        Text(text = "Vez do Jogador: $jogador", color = Color.Black, fontSize = 28.sp, fontWeight = FontWeight.Bold)
    }
}

// Componível para renderizar o tabuleiro do jogo
@Composable
fun Tabuleiro(modifier: Modifier = Modifier,
              onCelulaClicked: (row: Int, col: Int) -> Unit,
              tabuleiro: Array<Array<String>>) {

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(horizontal = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        for (i in tabuleiro.indices) {
            Row {
                for (j in tabuleiro[i].indices) {
                    // Celula individual
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

// Componível para renderizar uma célula do tabuleiro
@Composable
fun Celula(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(5.dp)
            .aspectRatio(1f),
        shape = RoundedCornerShape(0.dp),
    ) {
        Text(text = text, fontSize = 64.sp, color = Color.White, fontWeight = FontWeight.Bold)
    }

}

// Componível para exibir o botão "Jogar de Novo"
@Composable
fun JogarDeNovo(onJogarDeNovoClicked: () -> Unit, modifier: Modifier = Modifier){
    Column (
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Button(onClick = { onJogarDeNovoClicked() }) {
            Text(text = "Jogar de Novo", fontSize = 28.sp, color = Color.White)
        }
    }
}

// Componível para exibir a mensagem de fim de jogo
@Composable
fun FimDeJogo(vencedor: String) {
    if (vencedor == "Empate") {
        Text(
            text = "Deu Velha!\n O jogo Empatou.",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    } else {
        Text(
            text = "Parabéns, Jogador $vencedor! Você venceu!",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

// Pré-visualização do jogo
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JogoDaVelhaKotlinTheme {
        JogoDaVelha()
    }
}

