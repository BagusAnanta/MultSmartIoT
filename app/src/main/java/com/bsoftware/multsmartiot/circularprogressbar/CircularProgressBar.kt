package com.bsoftware.multsmartiot.circularprogressbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.multsmartiot.circularprogressbar.ui.theme.MultSmartIoTTheme

class CircularProgressBar : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultSmartIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CircularProgressBarView()
                }
            }
        }
    }
}

@Composable
fun CircularProgressBarView(
    number : Float = 70f,
    numberStyle : TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    size : Dp = 180.dp,
    indicatorThickness : Dp = 28.dp,
    animationDuration : Int = 1000,
    animationDelay : Int = 0,
    foregroundIndicatorColor : Color = Color(0xFF35898f),
    backgroundColorIndicatorColor : Color = Color.LightGray.copy(alpha = 0.3f),
    indicator : String = "%"
) {
    // number remember
    var numberRemember by remember {mutableStateOf(0f)}

    // for number animation
    val numberAnimation = animateFloatAsState(
        targetValue = numberRemember,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        ),
        label = "numberAnim"
    )

    // this launch after activity open
    LaunchedEffect(Unit){
        numberRemember = number
    }

    // make a progress circular
    Box(
        modifier = Modifier
            .size(size = size),
        contentAlignment = Alignment.Center
    ){
        Canvas(
            modifier = Modifier
                .size(size = size),
        ){
            drawCircle(
                color = backgroundColorIndicatorColor,
                radius = size.toPx() / 2,
                style = Stroke(
                    width = indicatorThickness.toPx(),
                    cap = StrokeCap.Round
                )
            )

            val sweepAngle = (numberAnimation.value / 100) * 360

            // foreground circle
            drawArc(
                color = foregroundIndicatorColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(indicatorThickness.toPx(), cap = StrokeCap.Round)
            )
        }

        Row {
            Text(
                text = (numberAnimation.value).toInt().toString(),
                style = numberStyle
            )
            Text(
                text = indicator,
                style = numberStyle,
                modifier = Modifier
                    .padding(start = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CircularProgressBarPreview() {
    MultSmartIoTTheme {
        CircularProgressBarView()
    }
}