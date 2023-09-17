package com.sigma.stepcounter

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * This class encapsulates the properties of a bottom navigation item, including its name,
 * route (destination), and icon.
 *
 * @param name The name of the navigation item. This could be used for display purposes.
 * @param route The route of the navigation item. This string represents the destination
 * associated with this navigation item in the application's navigation graph.
 * @param icon The icon of the navigation item as an ImageVector. This will be displayed in
 * the bottom navigation bar.
 */
data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)