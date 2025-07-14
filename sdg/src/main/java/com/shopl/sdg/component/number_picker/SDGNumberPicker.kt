package com.shopl.sdg.component.number_picker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

// Color definitions (replace with your actual color definitions if they are different)
val Neutral700 = Color(0xFF424242) // Example: Neutral/700
val Neutral400 = Color(0xFFAFAFAF) // Example: Neutral/400
val Neutral150 = Color(0xFFEEEEEE) // Example: Neutral/150

// Typography (replace with your actual typography if different)
object AppTypography {
    val Body1R: TextStyle = TextStyle(
        fontSize = 16.sp,
        // Add other relevant TextStyle properties like fontWeight, fontFamily, etc.
    )
}

@Composable
fun SDGNumberPicker(
    modifier: Modifier = Modifier,
    range: IntRange,
    currentValue: Int,
    onValueChange: (Int) -> Unit,
    height: Dp = 150.dp, // Default height, adjust as needed
    textStyle: TextStyle = AppTypography.Body1R,
    enabledColor: Color = Neutral700,
    disabledColor: Color = Neutral400,
    selectedBoxColor: Color = Neutral150,
    cornerRadius: Dp = 8.dp,
    enabled: Boolean = true
) {
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = currentValue - range.first)
    val coroutineScope = rememberCoroutineScope()
    val itemHeight = height / 3 // Assuming 3 items visible (selected, one above, one below)

    // Scroll to the current value when it changes externally
    LaunchedEffect(currentValue) {
        val targetIndex = (currentValue - range.first).coerceIn(0, range.last - range.first)
        if (listState.firstVisibleItemIndex != targetIndex) {
            coroutineScope.launch {
                listState.animateScrollToItem(targetIndex)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(range.count()) { index ->
                val itemValue = range.first + index
                val isSelected = itemValue == currentValue
                val textColor = if (enabled) enabledColor else disabledColor

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .then(
                            if (isSelected) Modifier
                                .padding(horizontal = 16.dp) // Adjust padding as needed
                                .clip(RoundedCornerShape(cornerRadius))
                                .background(selectedBoxColor)
                            else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemValue.toString(),
                        style = textStyle.copy(color = textColor),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // This is to simulate the snapping behavior and centered selection.
        // A more robust solution might involve custom LazyList behavior or
        // calculations based on scroll offset.
        LaunchedEffect(listState.isScrollInProgress) {
            if (!listState.isScrollInProgress) {
                val visibleItems = listState.layoutInfo.visibleItemsInfo
                if (visibleItems.isNotEmpty()) {
                    // Calculate the middle item based on its position relative to the viewport center
                    val viewportCenter = listState.layoutInfo.viewportSize.height / 2
                    val middleItem = visibleItems.minByOrNull {
                        kotlin.math.abs((it.offset + it.size / 2) - viewportCenter)
                    }

                    middleItem?.let {
                        val selectedIndex = it.index
                        val newValue = range.first + selectedIndex
                        if (currentValue != newValue) {
                            onValueChange(newValue)
                            // Snap to the selected item
                            coroutineScope.launch {
                                listState.animateScrollToItem(selectedIndex)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun SDGNumberPickerPreview() {
    var selectedValue by remember { mutableStateOf(9) }
    MaterialTheme { // It's good practice to wrap previews in MaterialTheme
        SDGNumberPicker(
            range = 1..20,
            currentValue = selectedValue,
            onValueChange = { selectedValue = it },
            height = 120.dp // Example height for preview
        )
    }
}

@Preview(showBackground = true, widthDp = 200)
@Composable
fun SDGNumberPickerDisabledPreview() {
    var selectedValue by remember { mutableStateOf(5) }
    MaterialTheme {
        SDGNumberPicker(
            range = 1..10,
            currentValue = selectedValue,
            onValueChange = { selectedValue = it },
            height = 120.dp,
            enabled = false
        )
    }
}
