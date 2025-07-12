package ru.otus.marketsample.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.R
import ru.otus.marketsample.ViewModelFactory
import ru.otus.marketsample.navigate.AppNavGraph
import ru.otus.marketsample.navigate.Screen
import ru.otus.marketsample.ui.details.DetailScreen
import ru.otus.marketsample.ui.product.ProductList
import ru.otus.marketsample.ui.promo.PromoList
import ru.otus.common.ui.R as DrawableCommon

@Composable
fun MainApplicationScreen(
    modifier: Modifier = Modifier,
    factory: ViewModelFactory
) {
    val navHostController = rememberNavController()
    val currentScreen = navHostController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                currentScreen = currentScreen,
                onClick = { screen ->
                    navHostController.navigate(screen.route) {
                        launchSingleTop = true
                        popUpTo(screen.route) {
                            saveState = true
                        }
                    }
                }
            )
        },
        modifier = modifier,
        content = { paddingValues ->
            AppNavGraph(
                navHostController = navHostController,
                promoContent = {
                    PromoList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
                        factory = factory,
                    )
                },
                productsContent = {
                    ProductList(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(paddingValues),
                        factory = factory,
                    ) { screen, id ->
                        navHostController.navigate((screen as Screen.Details).getRouteWithArgs(id)) {
                            launchSingleTop = true
                        }
                    }
                },
                detailsContent = {
                    DetailScreen(
                        id = it
                    ) {

                    }
                }
            )
        }
    )
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    currentScreen: String?,
    onClick: (Screen) -> Unit
) {
    val items = listOf(

        NavItem.ProductsItemNavigate(
            title = stringResource(R.string.title_products),
            drawableId = DrawableCommon.drawable.ic_list
        ),
        NavItem.PromoItemNavigate(
            title = stringResource(R.string.title_promo),
            drawableId = DrawableCommon.drawable.ic_discount
        )
    )
    NavigationBar(modifier = modifier) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(item.drawableId),
                            contentDescription = item.title
                        )
                        Text(
                            text = item.title,
                        )
                    }
                },
                selected = currentScreen == item.screen.route,
                onClick = {
                    onClick(item.screen)
                }
            )
        }
    }
}


@Preview
@Composable
private fun PreviewBottomBar() {
    BottomBar(
        currentScreen = stringResource(R.string.title_products),
        onClick = { screen -> }
    )
}
