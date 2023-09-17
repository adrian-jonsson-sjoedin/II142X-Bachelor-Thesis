package com.sigma.stepcounter.ui.composable


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Indication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.sigma.stepcounter.R
import com.sigma.stepcounter.ui.theme.DarkYellow
import kotlin.math.min
import kotlin.math.sqrt

/**
 * A class representing a hexagonal shape.
 *
 * Implements the [Shape] interface from the Compose UI graphics package.
 */
class HexagonShape : Shape {
    /**
     * Overrides the createOutline function from the [Shape] interface.
     *
     * @param size The size of the layout.
     * @param layoutDirection The direction of the layout.
     * @param density The density of the current layout context.
     * @return An [Outline] representing a hexagonal shape.
     */
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawCustomHexagonPath(size)
        )
    }
}

/**
 * Draws a custom hexagonal path based on the given size.
 *
 * @param size The size of the layout.
 * @return A [Path] representing a hexagonal shape.
 */
fun drawCustomHexagonPath(size: Size): Path {
    return Path().apply {
        val radius = min(size.width / 2f, size.height / 2f)
        customHexagon(radius, size)
    }
}

/**
 * Extension function to create a hexagonal path.
 *
 * @receiver [Path] The path to which the hexagon is added.
 * @param radius The radius of the hexagon.
 * @param size The size of the layout.
 */
fun Path.customHexagon(radius: Float, size: Size) {
    val triangleHeight = (sqrt(3.0) * radius / 2)
    val centerX = size.width / 2
    val centerY = size.height / 2

    moveTo(centerX, centerY + radius)
    lineTo((centerX - triangleHeight).toFloat(), centerY + radius / 2)
    lineTo((centerX - triangleHeight).toFloat(), centerY - radius / 2)
    lineTo(centerX, centerY - radius)
    lineTo((centerX + triangleHeight).toFloat(), centerY - radius / 2)
    lineTo((centerX + triangleHeight).toFloat(), centerY + radius / 2)
    close()
}

/**
 * Generates a custom hexagonal bar path based on the given size.
 *
 * @param size The size of the layout.
 * @return A [Path] representing a hexagonal bar shape.
 */
fun drawCustomHexagonBarPath(size: Size): Path {
    return Path().apply {
        customHexagonBar(size)
    }
}

/**
 * Adds hexagon bar shape to the current path.
 *
 * @receiver [Path] The path to which the hexagon bar is added.
 * @param size The size of the layout.
 */
fun Path.customHexagonBar(size: Size) {
    val width = size.width
    val height = size.height

    moveTo(0f, height / 2)
    lineTo(width * (2f / 25f), 0f)
    lineTo(width * (23f / 25f), 0f)
    lineTo(width, height / 2)
    lineTo(width * (23f / 25f), height)
    lineTo(width * (2f / 25f), height)
    lineTo(0f, height / 2)
    close()
}

/**
 * A composable function that creates a hexagonal bar that will take up the full space allocated
 * to it.
 *
 * @param modifier The modifier to be applied to the hexagonal bar.
 * @param backgroundColor The background color of the hexagonal bar.
 */
@Composable
fun HexagonBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.secondary,
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        var canvasSize by remember {
            mutableStateOf(Size.Zero)
        }
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            val height = size.height
            val width = size.width
            canvasSize = Size(width, height)
            val path = drawCustomHexagonBarPath(size)
            val roundEffect = PathEffect.cornerPathEffect(20f)
            drawIntoCanvas { canvas ->
                canvas.drawOutline(outline = Outline.Generic(path), paint = Paint().apply {
                    this.color = backgroundColor
                    this.style = PaintingStyle.Fill
                    this.pathEffect = roundEffect
                })
            }
        }
    }
}

/**
 * A composable function that creates a hexagonal shape.
 *
 * @param modifier The modifier to be applied to the hexagonal shape.
 * @param backgroundColor The background color of the hexagonal shape.
 * @param icon An optional icon that will be displayed inside the hexagon.
 * @param iconColor The color of the icon.
 */
@Composable
fun Hexagon(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    icon: Int? = null,
    iconColor: Color = MaterialTheme.colors.primary
) {
    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        val maxWidth = this.maxWidth
        var canvasSize by remember {
            mutableStateOf(Size.Zero)
        }
        Canvas(
            modifier = Modifier.size(maxWidth)
        ) {
            val height = size.height
            val width = size.width
            canvasSize = Size(width, height)
            val path = drawCustomHexagonPath(size)
            val roundEffect = PathEffect.cornerPathEffect(20f)
            drawIntoCanvas { canvas ->
                canvas.drawOutline(outline = Outline.Generic(path), paint = Paint().apply {
                    this.color = backgroundColor
                    this.style = PaintingStyle.Fill
                    this.pathEffect = roundEffect
                })
            }
        }
        val hexagonSize = maxWidth
        val iconSizePercentage = 0.3f // Adjust the percentage as desired
        val iconSize = (hexagonSize * iconSizePercentage).value
        val verticalOffset = (-iconSize / 4).dp
        if (icon != null) {
            Icon(
                painterResource(id = icon),
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier
                    .offset(y = verticalOffset)
                    .size(iconSize.dp)
            )
        }
    }
}

/**
 * A composable function that creates a hexagonal button.
 *
 * @param modifier The modifier to be applied to the hexagonal button.
 * @param icon An optional icon that will be displayed inside the button.
 * @param onButtonClick The action to perform when the button is clicked.
 * @param shouldGlow A boolean indicating whether the button should have a glowing effect.
 * @param iconColor The color of the icon.
 * @param backgroundColor The background color of the button.
 * @param indication The indication to show when interaction occurs. This can be a custom indication.
 */
@Composable
fun HexagonButton(
    modifier: Modifier = Modifier,
    icon: Int? = null,
    onButtonClick: () -> Unit = {},
    shouldGlow: Boolean = false,
    iconColor: Color = MaterialTheme.colors.background,
    backgroundColor: Color = MaterialTheme.colors.secondary,
    indication: Indication?
) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                onClick = onButtonClick,
                indication = indication,
                interactionSource = remember { MutableInteractionSource() }),
        contentAlignment = Alignment.Center
    ) {
        val maxWidth = this.maxWidth
        var canvasSize by remember {
            mutableStateOf(Size.Zero)
        }
        val paint = remember {
            Paint().apply {
                style = PaintingStyle.Stroke
                strokeWidth = 70f // width of glow/shadow
            }
        }
        val frameworkPaint = remember {
            paint.asFrameworkPaint()
        }
        Canvas(
            modifier = Modifier.size(maxWidth)
        ) {
            val height = size.height
            val width = size.width
            canvasSize = Size(width, height)
            val path = drawCustomHexagonPath(size)
            if (shouldGlow) {
                val transparent = DarkYellow.copy(alpha = 0f).toArgb()
                frameworkPaint.color = transparent
                frameworkPaint.setShadowLayer(
                    30f, // diffusion
                    0f, 0f, DarkYellow.copy(alpha = .8f) //transparency
                        .toArgb()
                )
                drawIntoCanvas { canvas ->
                    canvas.drawPath(path, paint = paint)
                }
            }
            val roundEffect = PathEffect.cornerPathEffect(20f)
            drawIntoCanvas { canvas ->
                canvas.drawOutline(outline = Outline.Generic(path), paint = Paint().apply {
                    this.color = backgroundColor
                    this.style = PaintingStyle.Fill
                    this.pathEffect = roundEffect
                })
            }
        }
        if (icon != null && shouldGlow) {
            Icon(
                painterResource(id = icon),
                contentDescription = "Button",
                modifier = Modifier.fillMaxSize(0.6f),
                tint = MaterialTheme.colors.primary
            )
        } else if (icon != null) {
            Icon(
                painterResource(id = icon),
                contentDescription = "Button",
                modifier = Modifier.fillMaxSize(0.6f),
                tint = iconColor
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun Preview() {
    Hexagon(icon = R.drawable.steps_48px)

}

@Preview(showBackground = true)
@Composable
fun BarPreview() {
    HexagonBar()
}