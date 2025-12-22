package ru.otus.marketsample.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.otus.common.ui.R

@Composable
fun BottomBar(
    navigationState: NavigationState,
    modifier: Modifier = Modifier,
) {
    val navStackBackEntry by navigationState.navHostController.currentBackStackEntryAsState()
    NavigationBar(modifier = modifier) {
        NavigationItem.entries.forEach { navigationItem ->
            val selected = navStackBackEntry?.destination?.hierarchy?.any {
                it.hasRoute(navigationItem.route::class)
            } == true

            val color = when {
                selected -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            }

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) navigationState.navigateInBottomBarTo(navigationItem.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(navigationItem.iconResId),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = color,
                    )
                },
                label = {
                    Text(
                        text = navigationItem.title,
                        color = color,
                    )
                },
            )
        }
    }
}

enum class NavigationItem(
    val route: Route,
    val title: String,
    val iconResId: Int,
) {
    PRODUCTS(Route.Products, "Products", R.drawable.ic_list),
    PROMO(Route.Promo, "Promo", R.drawable.ic_discount),
}
