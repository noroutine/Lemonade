package me.noroutine.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.noroutine.lemonade.ui.theme.LemonadeTheme

enum class LemonadeGameStep(val painterId : Int, val ctaTextId : Int) {
    TREE(R.drawable.lemon_tree, R.string.tap_lemon_tree),
    LEMON(R.drawable.lemon_squeeze, R.string.squeeze_lemon),
    DRINK(R.drawable.lemon_drink, R.string.tap_to_drink),
    DONE(R.drawable.lemon_restart, R.string.tap_to_start_again)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LemonadeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LemonadeScreen(
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize())
                }
            }
        }
    }
}

@Composable
fun LemonadeScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                lineHeight = 36.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xfff6e468))
            )
            LemonadeStep(Modifier.fillMaxSize())
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewLemonadeScreen() {
    LemonadeTheme {
        LemonadeScreen(Modifier.fillMaxSize())
    }
}

@Composable
fun LemonadeStep(modifier: Modifier = Modifier) {
    var gameStep by remember {
        mutableStateOf(LemonadeGameStep.TREE)
    }
    var squeezeCount by remember {
        mutableStateOf(1)
    }

    val imageResource = painterResource(gameStep.painterId)
    val ctaText = stringResource(gameStep.ctaTextId)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xfff4f5f5))
            .padding(16.dp)
    ) {
        Image(
            painter = imageResource,
            contentDescription = "Lemon tree",
            modifier = Modifier
                .height(200.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color(0xffcbebd4))
                .clickable {
                    gameStep = when {
                        gameStep == LemonadeGameStep.TREE -> {
                            squeezeCount = (2..4).random()
                            LemonadeGameStep.LEMON
                        }
                        gameStep == LemonadeGameStep.LEMON && squeezeCount > 1 -> {
                            squeezeCount--
                            LemonadeGameStep.LEMON
                        }
                        gameStep == LemonadeGameStep.LEMON && squeezeCount == 1 -> LemonadeGameStep.DRINK
                        gameStep == LemonadeGameStep.DRINK -> LemonadeGameStep.DONE
                        else -> LemonadeGameStep.TREE
                    }
                }
        )

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            text = ctaText,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun PreviewLemonadeStep() {
    LemonadeTheme {
        LemonadeStep()
    }
}