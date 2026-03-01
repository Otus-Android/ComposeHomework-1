package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.findNavController
import androidx.navigation.navigation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.otus.marketsample.details.feature.navigation.FragmentHost
import ru.otus.marketsample.details.feature.navigation.detailNavHost
import ru.otus.marketsample.di.DaggerAppComponent
import ru.otus.marketsample.di.ViewModelsFactory
import ru.otus.marketsample.navigation.MainDestination
import ru.otus.marketsample.navigation.Product
import ru.otus.marketsample.navigation.Products
import ru.otus.marketsample.navigation.Promo
import ru.otus.marketsample.products.feature.navigation.productGraph
import ru.otus.marketsample.promo.navigation.promoGraph
import javax.inject.Inject
import ru.otus.common.ui.R as RUi

private const val LENGTH_SHORT = 3000L

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelsFactory: ViewModelsFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        DaggerAppComponent.factory()
            .create(this)
            .inject(this)
        super.onCreate(savedInstanceState)

        val mainScreens = listOf(
            Products(),
            Promo()
        )

        setContent {
            var isShowSnackbar by remember {
                mutableStateOf(false)
            }
            MaterialTheme {
                var selected by remember {
                    mutableStateOf<MainDestination>(Products())
                }
                val navController = rememberNavController()
                MaterialTheme {
                    Scaffold(
                        containerColor = colorResource(RUi.color.white),
                        snackbarHost = {
                            AnimatedVisibility(
                                isShowSnackbar,
                                enter = slideInVertically { -it },
                                exit = slideOutVertically { it },
                            ) {
                                Snackbar() {
                                    Text(
                                        modifier = Modifier.padding(16.dp),
                                        text = "Error wile loading data"
                                    )
                                }
                                LaunchedEffect(isShowSnackbar) {
                                    launch {
                                        delay(LENGTH_SHORT)
                                        isShowSnackbar = false
                                    }
                                }
                            }
                        },
                        bottomBar = {
                            BottomAppBar(
                                containerColor = colorResource(RUi.color.white),
                                modifier = Modifier
                            ) {
                                mainScreens.forEach { destination ->
                                    NavigationBarItem(
                                        colors = NavigationBarItemDefaults.colors(
                                            indicatorColor = Color.Transparent,
                                            selectedTextColor = colorResource(RUi.color.purple_500),
                                            unselectedTextColor = colorResource(RUi.color.black),
                                            selectedIconColor = colorResource(RUi.color.purple_500),
                                            unselectedIconColor = colorResource(RUi.color.black),
                                        ),
                                        selected = selected == destination,
                                        onClick = {
                                            selected = destination
                                            navController.navigate(destination) {
                                                launchSingleTop = true
                                                restoreState = true
                                                popUpTo(Products()) {
                                                    saveState = true
                                                    inclusive = true
                                                }
                                            }
                                        },
                                        label = {
                                            Text(
                                                text = getString(destination.labelRes),
                                                fontSize = 12.sp,
                                                fontWeight = if (selected == destination) {
                                                    FontWeight.Bold
                                                } else {
                                                    FontWeight.Normal
                                                }
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                modifier = Modifier
                                                    .size(24.dp),
                                                painter = painterResource(destination.iconRes),
                                                contentDescription = null,
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    ) { paddingsValue ->
                        NavHost(
                            modifier = Modifier
                                .padding(paddingsValue),
                            navController = navController,
                            startDestination = Product,
                            enterTransition = {
                                slideInHorizontally() + fadeIn()
                            },
                            exitTransition = {
                                slideOutHorizontally() + fadeOut()
                            }
                        ) {
                            promoGraph(navController, viewModelsFactory)
                            navigation<Product>(startDestination = Products()) {
                                productGraph(
                                    navController = navController,
                                    viewModelFactory = viewModelsFactory,
                                    openDetail = {
                                        navController.navigate(FragmentHost(it))
                                    },
                                    showSnackbarCallback = { isShowSnackbar = it }
                                )
                                detailNavHost(navController)
                            }
                        }
                    }
                }
            }

        }
    }
}