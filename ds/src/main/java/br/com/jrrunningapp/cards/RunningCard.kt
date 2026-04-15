package br.com.jrrunningapp.cards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import br.com.jrrunningapp.text.RunningText
import br.com.jrrunningapp.text.RunningTextData

data class RunningCardData(
    @DrawableRes val icon: Int,
    val type: RunningCardSizeType,
    val smallText: RunningTextData,
    val bigText: RunningTextData,
    val backgroundColor: Color,
)

enum class RunningCardSizeType(val size: Offset) {
    DEFAULT(Offset(84.dp.value, 108.dp.value)),
    BIG(Offset(128.dp.value, 108.dp.value))
}

@Composable
fun RunningCard(
    modifier: Modifier = Modifier,
    data: RunningCardData
) {

    Column(
        modifier = modifier
            .background(data.backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = data.icon),
            contentDescription = null
        )

        RunningText(
            modifier = Modifier.padding(top = 6.dp),
            data = data.smallText
        )

        RunningText(
            modifier = Modifier.padding(top = 4.dp),
            data = data.bigText
        )
    }
}
