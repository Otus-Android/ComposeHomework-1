package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.products.ProductListScreen
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.promo.PromoListScreen
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
private fun MainScreen() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val appComponent = (context.applicationContext as MarketSampleApp).appComponent

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Products",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Products") {
                val factory = remember {
                    DaggerProductListComponent.factory()
                        .create(appComponent)
                        .getViewModelFactory()
                }

                val viewModel: ProductListViewModel = viewModel(factory = factory)

                ProductListScreen(
                    viewModel = viewModel
                )
            }
            composable("Promo") {
                val factory = remember {
                    DaggerPromoComponent.factory()
                        .create(appComponent)
                        .getViewModelFactory()
                }

                val viewModel: PromoListViewModel = viewModel(factory = factory)
                PromoListScreen(
                    viewModel = viewModel
                )

            }
        }
    }
}

@Composable
private fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(
            "Products",
            "Products",
            painterResource(ru.otus.common.ui.R.drawable.ic_list)
        ),
        BottomNavItem("Promo", "Promo", painterResource(ru.otus.common.ui.R.drawable.ic_discount))
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = item.icon,
                        contentDescription = item.title,
                        Modifier.size(24.dp)
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(val route: String, val title: String, val icon: Painter)