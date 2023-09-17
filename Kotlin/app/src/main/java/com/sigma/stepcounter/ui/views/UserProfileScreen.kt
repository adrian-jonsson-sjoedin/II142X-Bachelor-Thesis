package com.sigma.stepcounter.ui.views

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sigma.stepcounter.R
import com.sigma.stepcounter.ui.composable.HexagonBar
import com.sigma.stepcounter.ui.composable.HexagonButton
import com.sigma.stepcounter.ui.composable.HexagonShape
import com.sigma.stepcounter.ui.composable.drawCustomHexagonPath
import com.sigma.stepcounter.ui.theme.DarkGrey
import com.sigma.stepcounter.ui.theme.LemonadeYellow
import com.sigma.stepcounter.ui.theme.VeryYellow
import com.sigma.stepcounter.ui.viewModels.Gender
import com.sigma.stepcounter.ui.viewModels.UserProfileViewModel

/**
 * Composable for the user profile screen. This screen displays user information such as name,
 * weight, height, age, and gender. The user can update their information directly on this screen.
 *
 * @param modifier A modifier that can be used to adjust the layout of the composable.
 * @param userProfileViewModel The ViewModel that provides data and handles actions on this screen.
 */
@Composable
fun UserProfileScreen(
    modifier: Modifier = Modifier, userProfileViewModel: UserProfileViewModel
) {
    // Get the data from the ProfileUiState
    val profileUiState by userProfileViewModel.profileUiState.collectAsState()
    Column(
        modifier = modifier
            .padding(4.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        HexagonProfilePicture(fraction = 0.4f, profilePicture = R.drawable.test)
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            HexagonBar()
            Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                val textInput = rememberSaveable { mutableStateOf(profileUiState.userName) }
                val focusManager = LocalFocusManager.current
                TextField(
                    value = profileUiState.userName,
                    onValueChange = {
                        val newValue = it
                        userProfileViewModel.updateUserName(newValue)
                        textInput.value = it
                    },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = VeryYellow,
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            if (textInput.value.isEmpty()) {
                                textInput.value = "Name"
                                userProfileViewModel.updateUserName("Name")
                            }
                        }
                    ),
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.weight(1f)) {
            ProfileSetting(
                unit = "kg", value = profileUiState.userWeight.toString(),
                onUpdateValue = userProfileViewModel::updateUserWeight
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.weight(1f)) {
            ProfileSetting(
                unit = "cm", value = profileUiState.userHeight.toString(),
                onUpdateValue = userProfileViewModel::updateUserHeight
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(modifier = Modifier.weight(1f)) {
            ProfileSetting(
                unit = "years", value = profileUiState.userAge.toString(),
                onUpdateValue = userProfileViewModel::updateUserAge
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        GenderButtons(
            userGender = profileUiState.userGender,
            updateUserGender = userProfileViewModel::updateUserGender
        )
        Spacer(modifier = Modifier.height(25.dp))
    }
}

/**
 * Composable function that creates gender selection buttons.
 *
 * @param userGender Current selected gender.
 * @param updateUserGender Function to be called when gender is updated.
 */
@Composable
fun GenderButtons(userGender: Gender?, updateUserGender: (Gender) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Box(Modifier.size(100.dp)) {
            HexagonButton(
                icon = R.drawable.baseline_male_24,
                onButtonClick = { updateUserGender(Gender.MALE) },
                shouldGlow = (userGender == Gender.MALE),
                indication = rememberRipple(color = LemonadeYellow, bounded = false)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(Modifier.size(100.dp)) {
            HexagonButton(
                icon = R.drawable.baseline_female_24,
                onButtonClick = { updateUserGender(Gender.FEMALE) },
                shouldGlow = (userGender == Gender.FEMALE),
                indication = rememberRipple(color = LemonadeYellow, bounded = false)
            )
        }
    }
}

/**
 * This composable function creates a ProfileSetting section for a given parameter. It displays the current
 * value of the parameter and allows users to increment, decrement or manually input the value.
 *
 * @param unit The measurement unit of the parameter.
 * @param value The current value of the parameter.
 * @param onUpdateValue Function to be called when value is updated.
 * @param keyboardType The type of keyboard to be used for input. Default is numeric.
 */
/* TODO: Need to fix the styling of the text field*/
@Composable
fun ProfileSetting(
    unit: String,
    value: String,
    onUpdateValue: (Double) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Number
) {
    val textInput = rememberSaveable { mutableStateOf(value) }
    val focusManager = LocalFocusManager.current

    Column {
        Box(modifier = Modifier.fillMaxWidth()) {
            HexagonBar()
            Row(
                modifier = Modifier
                    .matchParentSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HexagonButton(
                    modifier = Modifier
                        .weight(0.2f),
                    onButtonClick = {
                        val currentValue = textInput.value.toDoubleOrNull() ?: 0.0
                        val newValue = (currentValue - 1).coerceAtLeast(0.0)
                        onUpdateValue(newValue)
                        textInput.value = newValue.toString()
                    },
                    iconColor = DarkGrey,
                    backgroundColor = VeryYellow,
                    icon = R.drawable.baseline_minus_24,
                    indication = rememberRipple(color = LemonadeYellow, bounded = false)
                )
                Box(modifier = Modifier.weight(0.7f)) {
                    TextField(
                        modifier = Modifier.padding(20.dp, 0.dp),
                        value = "${textInput.value + " " + unit}",
                        maxLines = 1,
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        onValueChange =
                        {
                            val newValue = it.toDoubleOrNull()
                            if (newValue != null) {
                                onUpdateValue(newValue)
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
                                    textInput.value = "0.0"
                                    onUpdateValue(0.0)
                                }
                            }
                        ),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.White,
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = VeryYellow,
                        )
                    )
                }
                HexagonButton(
                    modifier = Modifier
                        .weight(0.2f),
                    onButtonClick = {
                        val currentValue = textInput.value.toDoubleOrNull() ?: 0.0
                        val newValue = (currentValue + 1).coerceAtLeast(0.0)
                        onUpdateValue(newValue)
                        textInput.value = newValue.toString()
                    },
                    iconColor = DarkGrey,
                    backgroundColor = VeryYellow,
                    icon = R.drawable.baseline_add_24,
                    indication = rememberRipple(color = LemonadeYellow, bounded = false)
                )
            }
        }
    }
}


/**
 * A Composable function that displays an image in the shape of a hexagon.
 *
 * @param fraction A float that defines what fraction of its parent's width the image will fill.
 * @param profilePicture An integer representing the Drawable resource ID of the image to display.
 * @param modifier A Modifier which can be used to adjust the layout or drawing context of the composable.
 */
@Composable
fun HexagonProfilePicture(
    fraction: Float,
    @DrawableRes profilePicture: Int,
    modifier: Modifier = Modifier
) {
    val myShape = HexagonShape()
    val primaryColor = MaterialTheme.colors.secondary
    Box(modifier = Modifier
        .fillMaxWidth(fraction)
        .aspectRatio(1f)
        .padding(10.dp)
        .drawWithContent {
            drawContent()
            drawPath(
                path = drawCustomHexagonPath(size),
                color = primaryColor,
                style = Stroke(
                    width = 10.dp.toPx(), //this is the hexagons stroke width
                    pathEffect = PathEffect.cornerPathEffect(20f)
                )
            )
        }
        .wrapContentSize()
    ) {
        Image(
            painter = painterResource(id = profilePicture),
            contentDescription = "My Hexagon Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .wrapContentSize()
                .graphicsLayer {
                    shadowElevation = 8.dp.toPx()
                    shape = myShape
                    clip = true
                }
                .background(color = Color.Cyan)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    val mockUpdateUserWeight: (Double) -> Unit = { }
    Box(modifier = Modifier.fillMaxHeight(0.65f)) {
        ProfileSetting(unit = "Preview", value = "13", onUpdateValue = mockUpdateUserWeight)
    }
}

@Preview(showBackground = true)
@Composable
fun GenderButtonsPreview() {
    val mockUpdateUserGender: (Gender) -> Unit = { /* Do nothing in the preview */ }
    GenderButtons(userGender = Gender.MALE, updateUserGender = mockUpdateUserGender)
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {

    UserProfileScreen(userProfileViewModel = viewModel())
}