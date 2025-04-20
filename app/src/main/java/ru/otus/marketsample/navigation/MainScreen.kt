package ru.otus.marketsample.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.R
import ru.otus.common.ui.R as UiR

@Composable
fun MainScreen(
    navController: NavHostController,
) {
    val bottomNavigationNavController = rememberNavController()
    Scaffold(
        bottomBar = {
            MarketSampleNavigationBar(
                navController = bottomNavigationNavController,
            )
        },
        content = { paddingValues ->
            BottomNavigationNavGraph(
                hostNavController = navController,
                bottomNavigationNavController = bottomNavigationNavController,
                paddingValues = paddingValues,
            )
        },
    )
}

@Composable
fun MarketSampleNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White,
    ) {
        val navItems = remember {
            listOf(
                BottomNavigationItem(
                    route = MarketSampleScreens.PRODUCTS_LIST.name,
                    iconRes = UiR.drawable.ic_list,
                    titleRes = R.string.title_products,
                ),
                BottomNavigationItem(
                    MarketSampleScreens.PROMO_LIST.name,
                    iconRes = UiR.drawable.ic_discount,
                    titleRes = R.string.title_promo,
                )
            )
        }
        val currentEntry by navController.currentBackStackEntryAsState()
        val currentRoute = remember(currentEntry) { currentEntry?.destination?.route }

        navItems.forEach { item ->
            val isSelected = currentRoute == item.route
            val color = if (isSelected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.secondary
            NavigationBarItem(
                selected = isSelected,
                icon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(item.iconRes),
                        tint = color,
                        contentDescription = null,
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.titleRes),
                        color = color,
                    )
                },
                onClick = { navController.navigate(item.route) }
            )
        }
    }
}

