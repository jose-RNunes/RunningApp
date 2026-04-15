package br.com.jrrunningapp.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class RunningTextData(
    val text: String,
    val textColor: Color,
    val textSize: RunningTextSizeType,
    val fontWeight: FontWeight
)

enum class RunningTextSizeType(val textSize: TextUnit) {
    SMALL(textSize = 12.sp),
    MEDIUM(textSize = 16.sp),
    LARGE(textSize = 20.sp)
}

@Composable
fun RunningText(modifier: Modifier = Modifier, data: RunningTextData) {
    Text(
        modifier = modifier,
        text = data.text,
        color = data.textColor,
        fontSize = data.textSize.textSize,
        fontWeight = data.fontWeight
    )
}

