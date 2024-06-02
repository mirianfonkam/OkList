package com.mdevor.oklist.presentation.uicomponents

import androidx.annotation.IntRange
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mdevor.oklist.presentation.theme.OkListBlueOnSurface
import com.mdevor.oklist.presentation.theme.OkListGrey500
import com.mdevor.oklist.presentation.theme.OkListTheme

const val PERCENTAGE_MAX_VALUE = 100

@Composable
fun OkTaskProgressCircle(
    modifier: Modifier = Modifier,
    @IntRange(from = 0, to = PERCENTAGE_MAX_VALUE.toLong())
    progressPercentageValue: Int,
    progressColor: Color,
    progressTextSize: TextUnit,
    progressTextColor: Color,
) {
    var boxSize by remember { mutableIntStateOf(0) }
    Box(
        modifier = modifier.onGloballyPositioned { coordinates ->
            boxSize = coordinates.size.width
        },
        contentAlignment = androidx.compose.ui.Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val width = size.width
            val height = size.height
            val circleThickness = width / 16f
            val circleRadius = size.minDimension / 2f - circleThickness
            val circleCenter = Offset(x = width / 2f, y = height / 2f)
            val circleDiameter = circleRadius * 2f

            // Draw the circle with the secondary color - remaining progress part
            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = OkListGrey500,
                radius = circleRadius,
                center = circleCenter
            )

            // Draw the circle with the primary color - current progress part
            drawArc(
                color = progressColor,
                startAngle = 90f,
                sweepAngle = (360f / PERCENTAGE_MAX_VALUE) * progressPercentageValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(width = circleDiameter, height = circleDiameter),
                topLeft = Offset(
                    (width - circleDiameter) / 2f,
                    (height - circleDiameter) / 2f
                )
            )
        }

        if (progressPercentageValue != PERCENTAGE_MAX_VALUE) {
            Text(
                text = "$progressPercentageValue%",
                style = TextStyle(
                    color = progressTextColor,
                    fontSize = progressTextSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
            )
        } else {
            val completedIconSize = (boxSize * 0.1f).dp
            Surface(
                color = progressColor,
                shape = CircleShape,
                modifier = Modifier.size(completedIconSize)
            ) {
                OkIcon(
                    imageVector = Icons.Rounded.Done,
                    contentDescription = "Task completed, 100%",
                    modifier = Modifier.size(completedIconSize)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OkTaskProgressPreview() {
    OkListTheme {
        OkTaskProgressCircle(
            modifier = Modifier.size(150.dp),
            progressPercentageValue = 70,
            progressColor = OkListBlueOnSurface,
            progressTextSize = 30.sp,
            progressTextColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}