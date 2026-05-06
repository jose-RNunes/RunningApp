package br.com.jrrunningapp.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
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

@Preview(showBackground = false)
@Composable
fun RunningTextPreview() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        RunningText(
            data = RunningTextData(
                text = "Run 3 days this week",
                textColor = Color.White,
                textSize = RunningTextSizeType.SMALL,
                fontWeight = FontWeight.Normal
            )
        )

        RunningText(
            data = RunningTextData(
                text = "Run 3 days this week",
                textColor = Color.White,
                textSize = RunningTextSizeType.MEDIUM,
                fontWeight = FontWeight.Medium
            )
        )

        RunningText(
            data = RunningTextData(
                text = "Run 3 days this week",
                textColor = Color.White,
                textSize = RunningTextSizeType.LARGE,
                fontWeight = FontWeight.Bold
            )
        )
    }
}
