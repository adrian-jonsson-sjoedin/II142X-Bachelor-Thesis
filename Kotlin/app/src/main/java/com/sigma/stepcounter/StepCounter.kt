package com.sigma.stepcounter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sigma.stepcounter.ui.theme.DarkGrey
import com.sigma.stepcounter.ui.theme.LightGrey
import com.sigma.stepcounter.ui.theme.NeutralGrey
import com.sigma.stepcounter.ui.viewModels.ActivityTrackingViewModel
import com.sigma.stepcounter.ui.viewModels.UserProfileViewModel
import com.sigma.stepcounter.ui.views.ActivityTrackingScreen
import com.sigma.stepcounter.ui.views.UserProfileScreen

/**
 * Main composable function for the app. It handles the navigation between different screens
 * and displays a bottom navigation bar for user interaction.
 *
 * @param modifier A modifier that can be used to adjust the layout of the composable.
 */
@Composable
fun StepCounterApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val userProfileViewModel = UserProfileViewModel()
    val activityTrackingViewModel = ActivityTrackingViewModel()
    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                items = listOf(
                    BottomNavItem(
                        name = "User",
                        route = "user",
                        icon = Icons.Default.Settings
                    ),
                    BottomNavItem(
                        name = "Main",
                        route = "main",
                        icon = Icons.Default.Home
                    )
                ),
                navController = navController,
                onItemClick = {
                    navController.navigate(it.route)
                }
            )
        },
        content = { paddingValues ->
            val colorStops = if (isSystemInDarkTheme()) {
                arrayOf(
                    0.0f to NeutralGrey,
                    0.15f to DarkGrey,
                    1f to DarkGrey
                )
            } else {
                arrayOf(
                    0.0f to Color.White,
                    0.3f to LightGrey,
                    1f to NeutralGrey
                )
            }// Background image for the whole app.
            Image(
                painter = painterResource(id = R.drawable.hexpattern_x1),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colorStops = colorStops
                        )
                    ),
//                colorFilter = ColorFilter.tint(AlmostRed), //change hexagon grid color
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Navigation(
                    navController = navController,
                    userProfileViewModel = userProfileViewModel,
                    activityTrackingViewModel = activityTrackingViewModel
                )
            }
        }
    )
}

/**
 * This Composable function handles the navigation between different screens within the app.
 *
 * @param navController The NavController used for navigating between screens.
 * @param userProfileViewModel The ViewModel that provides data and handles actions for the user profile screen.
 * @param activityTrackingViewModel The ViewModel that provides data and handles actions for the activity tracking screen.
 */
@Composable
fun Navigation(
    navController: NavHostController,
    userProfileViewModel: UserProfileViewModel,
    activityTrackingViewModel: ActivityTrackingViewModel
) {
    NavHost(
        navController = navController, startDestination = "main"
    ) {
        composable(route = "main") {
            ActivityTrackingScreen(activityTrackingViewModel = activityTrackingViewModel)

        }
        composable(route = "user") {
            UserProfileScreen(userProfileViewModel = userProfileViewModel)

        }
    }
}

/**
 * This @Composable function creates a BottomNavigationBar that allows users to navigate between different screens.
 *
 * @param modifier A modifier that can be used to adjust the layout of the composable.
 * @param items A list of BottomNavItem objects representing the items in the navigation bar.
 * @param navController The NavController used for navigating between screens.
 * @param onItemClick Function to be called when an item in the navigation bar is clicked.
 */
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavItem>,
    navController: NavController,
    onItemClick: (BottomNavItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {
        items.forEach() { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                selectedContentColor = MaterialTheme.colors.onPrimary,
                unselectedContentColor = NeutralGrey,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.name
                        )
                    }
                }
            )
        }
    }
}