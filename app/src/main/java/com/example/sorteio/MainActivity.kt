package com.example.sorteio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.sorteio.ui.theme.SorteioTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SorteioTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceRollerApp() {
    var dice1 by remember { mutableStateOf(1) }
    var dice2 by remember { mutableStateOf(1) }
    var winCount by remember { mutableStateOf(0) }
    var totalRolls by remember { mutableStateOf(0) }

    val resultMessage = when {
        dice1 + dice2 == 7 || dice1 + dice2 == 11 -> "Você ganhou!"
        else -> "Você perdeu!"
    }

    val winPercentage = if (totalRolls > 0) {
        (winCount.toFloat() / totalRolls * 100).toInt()
    } else {
        0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DiceImage(dice1)
                    DiceImage(dice2)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val newDice1 = Random.nextInt(1, 7)
                    val newDice2 = Random.nextInt(1, 7)
                    dice1 = newDice1
                    dice2 = newDice2
                    totalRolls += 1
                    if (newDice1 + newDice2 == 7 || newDice1 + newDice2 == 11) {
                        winCount += 1
                    }
                }) {
                    Text(text = "Jogar")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = resultMessage)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Score: $winCount/$totalRolls = $winPercentage%")
            }
        }
    )
}

@Composable
fun DiceImage(diceNumber: Int) {
    val imageResId = when (diceNumber) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_1
    }
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = "Dice $diceNumber",
        modifier = Modifier.size(64.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DiceRollerAppPreview() {
    SorteioTheme {
        DiceRollerApp()
    }
}
