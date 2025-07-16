package com.shopl.sdg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.number_picker.SDGNumberPicker
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShoplDesignGuideTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var currentValue by remember { mutableIntStateOf(50) }
                    var myValue by remember { mutableIntStateOf(50) }
                    var values = remember {
                        (0..90).map { it.toString() }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$myValue",
                            modifier = Modifier
                                .height(150.dp)
                                .background(SDGColor.GreenG)
                        )
//                        SDGNumberPicker(
//                            value = currentValue,
//                            onValueChange = { currentValue = it },
//                            range = 10..99,
//                        )


                        SDGNumberPicker(
                            range = (20..80),
                            value = myValue,
                            onValueChange = { myValue = it },
                        )

                    }
                }
            }
        }
    }
}

//@Composable
//fun MyNumberPicker(
//    modifier: Modifier = Modifier,
//    list: List<String>,
//    state: LazyListState,
//    flingBehavior: FlingBehavior,
//    textColor: Color = SDGColor.Neutral700
//) {
//    Box(modifier = modifier)
//    {
//        val shownCount = 3
//        LazyColumn(
//            state = state,
//            flingBehavior = flingBehavior,
//            modifier = Modifier
//                .height(150.dp)
//                .background(SDGColor.GreenG)
//                .graphicsLayer { compositingStrategy = CompositingStrategy.Offscreen }
////                .drawWithContent {
////                    drawContent()
////                    drawRect(
////                        brush = Brush.verticalGradient(
////                            0f to Color.Transparent,
////                            0.5f to Color.Black,
////                            1f to Color.Transparent
////                        ),
////                        blendMode = BlendMode.DstIn
////                    )
////                }
//        ) {
//            items(list.size) { index ->
//                Box(
//                    modifier = Modifier
//                        .height(50.dp)
//                        .clip(RoundedCornerShape(SDGCornerRadius.Radius8))
//                        .background(SDGColor.Neutral150)
//                ) {
//                    when (index) {
//                        0 -> {
//                            SDGText(
//                                text = " ",
//                                typography = SDGTypography.Body1R,
//                                textColor = textColor
//                            )
//                        }
//
//                        list.size - 1 -> {
//                            Box(
//                                modifier = Modifier
//                                    .height(50.dp)
//                                    .clip(RoundedCornerShape(SDGCornerRadius.Radius8))
//                                    .background(SDGColor.Neutral150)
//                            ) {
//                                SDGText(
//                                    text = " ",
//                                    typography = SDGTypography.Body1R,
//                                    textColor = textColor
//                                )
//                            }
//                        }
//
//                        else -> {
//                            SDGText(
//                                text = "${list[index]}",
//                                typography = SDGTypography.Body1R,
//                                textColor = textColor
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoplDesignGuideTheme {
        Greeting("Android")
    }
}