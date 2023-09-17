package com.sigma.stepcounter.ui.views

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sigma.stepcounter.R
import com.sigma.stepcounter.ui.composable.Hexagon
import com.sigma.stepcounter.ui.theme.VeryYellow
import com.sigma.stepcounter.ui.viewModels.ActivityTrackingViewModel
import com.wajahatkarim.flippable.FlipAnimationType
import com.wajahatkarim.flippable.Flippable
import com.wajahatkarim.flippable.FlippableController
import com.wajahatkarim.flippable.FlippableState
/**
 * Composable for the activity tracking screen.
 *
 * This screen displays the different flip activities and passes the relevant functions through to
 * them.
 *
 * @param modifier Modifier to be applied to this composable.
 * @param activityTrackingViewModel The ViewModel that provides data and handles actions on this screen.
 */
@Composable
fun ActivityTrackingScreen(
    modifier: Modifier = Modifier,
    activityTrackingViewModel: ActivityTrackingViewModel
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, 16.dp, 16.dp, 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()
            ) {}
            Box(
                modifier = Modifier
                    .weight(0.8f) // Equal weight for the second FlipActivity in the row
                    .fillMaxWidth()
            ) {
                FlipActivity(
                    activityTrackingViewModel = activityTrackingViewModel,
                    icon = R.drawable.icon_steps
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()
            ) {}
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(1f) // Equal weight for the first FlipActivity in the row
                    .fillMaxWidth()
            ) {
                FlipActivity(
                    activityTrackingViewModel = activityTrackingViewModel,
                    icon = R.drawable.icon_calories
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f) // Equal weight for the second FlipActivity in the row
                    .fillMaxWidth()
            ) {
                FlipActivity(
                    activityTrackingViewModel = activityTrackingViewModel,
                    icon = R.drawable.icon_distance
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
            ) {}
            Box(
                modifier = Modifier
                    .weight(0.5f) // Equal weight for the second FlipActivity in the row
                    .fillMaxWidth()
            ) {
                FlipActivity(
                    activityTrackingViewModel = activityTrackingViewModel,
                    icon = R.drawable.icon_barchart
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.25f)
                    .fillMaxWidth()
            ) {}
        }
    }
}

/**
 * Composable for a flippable activity tracker.
 *
 * This tracker displays current progress and the goal, and flips to allow editing of the goal.
 *
 * @param activityTrackingViewModel The ViewModel to interact with.
 * @param icon The resource ID of the icon to be displayed on the front page.
 */
@Composable
fun FlipActivity(
    activityTrackingViewModel: ActivityTrackingViewModel,
    icon: Int
) {
    val flipDuration = 400
    val flipAnimationType = FlipAnimationType.HORIZONTAL_ANTI_CLOCKWISE
    val flipController = remember(key1 = "2") {
        FlippableController()
    }

    var side by remember { mutableStateOf(FlippableState.INITIALIZED) }
//    Log.d("SideState", "${side}")


    Flippable(
        flipDurationMs = flipDuration,
        flipOnTouch = true,
        flipController = flipController,
        flipEnabled = true,

        frontSide = {
            HexagonActivityFront(
                value = activityTrackingViewModel.countUpFlow.collectAsState(initial = 0).value.toString(),
                goal = activityTrackingViewModel.getGoalValue().toString(),
                icon = icon
            )

        },
        backSide = {
            HexagonActivityBack(
                goal = activityTrackingViewModel.getGoalValue().toString(),
                setGoalValue = activityTrackingViewModel::setGoalValue,
                currentSide = side
            )

        },
        modifier = Modifier
            .wrapContentHeight(),
        contentAlignment = Alignment.TopCenter,
        onFlippedListener = { currentSide ->
            side = currentSide
            println(currentSide)
        },

        flipAnimationType = flipAnimationType
    )
}
/**
 * Composable for the front side of the activity tracker hexagon.
 *
 * Displays the current progress and the goal.
 *
 * @param modifier Modifier to be applied to this composable.
 * @param value The current progress towards the goal.
 * @param goal The goal value.
 * @param icon The resource ID of the icon to be displayed.
 * @param iconColor The color to be used for the icon.
 */
@Composable
fun HexagonActivityFront(
    modifier: Modifier = Modifier,
    value: String,
    goal: String,
    icon: Int,
    iconColor: Color = MaterialTheme.colors.primary
) {
    Box {
        Hexagon(icon = icon, iconColor = iconColor)
        Column(
            modifier = modifier
                .align(Alignment.Center)
                .padding(0.dp, 0.dp, 0.dp, 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = goal,
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                color = Color.White
            )
            Text(
                text = value,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                color = Color.White
            )
        }
    }
}
/**
 * Composable for the back side of the activity tracker hexagon.
 *
 * Allows for editing of the goal value.
 *
 * @param modifier Modifier to be applied to this composable.
 * @param setGoalValue Function to set the goal value.
 * @param goal The current goal value.
 * @param currentSide The current side of the flip (front or back).
 * @param keyboardType The keyboard type to be used for input.
 */
@Composable
fun HexagonActivityBack(
    modifier: Modifier = Modifier,
    setGoalValue: (Int) -> Unit,
    goal: String,
    currentSide: FlippableState,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    val hexagonColor = MaterialTheme.colors.secondary
    Log.d("SideState", "$currentSide")
    val textInput = rememberSaveable { mutableStateOf(goal) }
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    Box {
        Hexagon()
        Column(
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.8f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,

            ) {
            Text(
                text = "Change daily goal",
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.White
            )
            Box(
                modifier = Modifier.drawBehind {
                    val borderSize = 1.dp.toPx()
                    drawLine(
                        color = if (!isFocused) VeryYellow else hexagonColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = borderSize
                    )
                },

                ) {
                BasicTextField(
                    value = textInput.value,
                    enabled = currentSide == FlippableState.BACK,
                    cursorBrush = Brush.verticalGradient(colors = listOf(VeryYellow, VeryYellow)),
                    textStyle = TextStyle(
                        color = Color.White,
//                        textAlign = TextAlign.Center,
                        fontSize = 24.sp,
                    ),
                    onValueChange =
                    {
                        val newValue = it.toIntOrNull()
                        if (newValue != null) {
                            setGoalValue(newValue)
                        }
                        textInput.value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = keyboardType,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (textInput.value.isEmpty()) {
                                textInput.value = "0"
                                setGoalValue(0)
                            }
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Row {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colors.primary
                            )
                            innerTextField()
                        }
                    },
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged { isFocused = it.isFocused }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivityTrackingPreview() {
    ActivityTrackingScreen(activityTrackingViewModel = viewModel())
}

@Preview(showBackground = true)
@Composable
fun ActivityFrontPreview() {
    HexagonActivityFront(value = "1337", goal = "10000", icon = R.drawable.steps_48px)
}