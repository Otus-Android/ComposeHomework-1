package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.otus.marketsample.details.DetailsScreen
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.DetailsViewModelFactory
import ru.otus.marketsample.navigation.Routes
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductListViewModelFactory
import ru.otus.marketsample.products.feature.ProductsScreen
import ru.otus.marketsample.promo.PromoScreen
import ru.otus.marketsample.promo.feature.PromoListViewModel
import ru.otus.marketsample.promo.feature.PromoListViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var productsFactory: ProductListViewModelFactory

    @Inject
    lateinit var promoFactory: PromoListViewModelFactory

    @Inject
    lateinit var detailsFactory: DetailsViewModelFactory

    private val productViewModel: ProductListViewModel by viewModels<ProductListViewModel> { productsFactory }

    private val promoViewModel: PromoListViewModel by viewModels<PromoListViewModel> { promoFactory }

    private val detailsViewModel: DetailsViewModel by viewModels<DetailsViewModel> { detailsFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MarketSampleApp).appComponent.inject(this)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                content = { innerPadding ->
                    ScreenMain(Modifier.padding(innerPadding), navController)
                },
                bottomBar = { BottomNavigationBar(navController, Modifier) }
            )
        }
    }

    @Composable
    fun ScreenMain(modifier: Modifier, navController: NavHostController) {
        NavHost(navController, Routes.Products.route, modifier = modifier) {
            composable(Routes.Products.route) {
                ProductsScreen(Modifier, productViewModel, navController)
            }
            composable(Routes.Promo.route) {
                PromoScreen(Modifier, promoViewModel)
            }
            composable(Routes.Details.route + "/{id}") { navBackStack ->
                val id = navBackStack.arguments?.getString("id") ?: ""
                DetailsScreen(id, Modifier, detailsViewModel)
            }
        }
    }

    @Composable
    fun BottomNavigationBar(
        navController: NavController,
        modifier: Modifier
    ) {
        val currentRoute = remember { mutableStateOf<Routes>(Routes.Products) }
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = modifier.height(40.dp)
        ) {
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(ru.otus.common.ui.R.drawable.ic_list),
                        contentDescription = "products_list",
                        modifier = Modifier.height(24.dp),
                        if (currentRoute.value == Routes.Products)
                            colorResource(ru.otus.common.ui.R.color.purple_500)
                        else
                            colorResource(ru.otus.common.ui.R.color.black)
                    )
                },
                label = {
                    Text(stringResource(R.string.title_products))
                },
                onClick = {
                    currentRoute.value = Routes.Products
                    navController.navigate(Routes.Products.route)
                },
                selected = currentRoute == Routes.Products
            )
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(ru.otus.common.ui.R.drawable.ic_discount),
                        contentDescription = "products_list",
                        modifier = Modifier.height(24.dp),
                        tint = if (currentRoute.value == Routes.Promo)
                            colorResource(ru.otus.common.ui.R.color.purple_500)
                        else
                            colorResource(ru.otus.common.ui.R.color.black)
                    )
                },
                label = {
                    Text(
                        stringResource(R.string.title_promo)
                    )
                },
                onClick = {
                    currentRoute.value = Routes.Promo
                    navController.navigate(Routes.Promo.route)
                },
                selected = currentRoute == Routes.Promo
            )
        }
    }
}