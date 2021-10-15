package com.jackBeBack.kronos

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import com.jackBeBack.kronos.ui.theme.KronosTheme
import java.nio.file.WatchEvent
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContent {
            var currentScreen by remember { mutableStateOf(Screens.START) }
            KronosTheme {
                when (currentScreen) {
                    Screens.START -> startScreen(){newScreen ->
                        currentScreen = newScreen
                    }
                    Screens.MAIN -> mainScreen()
                }

            }
        }
    }


}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    KronosTheme {
        Greeting("Android")
    }
}

//@Preview(name = "Start Screen", device = Devices.PIXEL_C)
@Composable
fun startScreen(callback: (newScreen: Screens) -> Unit = {}) {
    val textStyle = TextStyle(color = Color.White, fontSize = 64.sp)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
            ClickableText(text = AnnotatedString("Start"), onClick = {
                callback(Screens.MAIN)
            }, style = textStyle)
            ClickableText(text = AnnotatedString("Exit"), onClick = {

            }, style = textStyle)
            ClickableText(text = AnnotatedString("Setting"), onClick = {

            }, style = textStyle)
        }
    }
}

//@Preview(name = "Main Screen", device = Devices.PIXEL_C)
@Composable
private fun mainScreen() {
    val fontSize = 64.sp
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        for (i in 0..2){
            val card = Card(CardEntity("Ich bin Karte $i"))
        }
    }
}

@Preview
@Composable
fun Card(cardEntity: CardEntity = CardEntity.default) {
    var pos by remember {
        mutableStateOf(Offset(0F, 0F))
    }
    var size by remember {
        mutableStateOf(Size(100F, 150F))}

    var card by remember {
        mutableStateOf(cardEntity)
    }
    var selected by remember {
        mutableStateOf(false)
    }

        
    Box(modifier = Modifier.fillMaxSize()) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(if (selected) Color.Green else Color.White)
                .size(size.width.dp, size.height.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                        pos = Offset(offsetX, offsetY)
                    }
                }
                .clickable { selected = !selected }
                .align(Alignment.Center)
        ){
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (image, description) = createRefs()

                Box(modifier = Modifier
                    .size((size.width - 10).dp, (size.height / 2).dp)
                    .constrainAs(image) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                ){
                    Image(modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = R.drawable.thumbnail),
                        contentDescription = "thumbnail")
                }
                Text(modifier = Modifier
                    .constrainAs(description) {
                        top.linkTo(image.bottom)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(start = 10.dp, end = 10.dp),text = card.description
                ,textAlign = TextAlign.Center)
            }
        }
    }
}

enum class Screens {
    START,
    MAIN,
    PLAYGROUND
}

enum class CardType {
    ATTACK,
    SKILL,
    POWER
}