package com.example.jogodavelhakotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
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
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )

        Column (
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ){
            VezJogador(
                jogador = "",
                modifier = modifier
            )

            Tabuleiro(
                modifier = modifier
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
fun Tabuleiro(modifier: Modifier = Modifier){

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