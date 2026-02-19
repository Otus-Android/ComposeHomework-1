package ru.otus.marketsample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.findNavController
import androidx.navigation.toRoute
import ru.otus.marketsample.databinding.ActivityMainBinding
import ru.otus.marketsample.details.feature.DetailsViewModel
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.products.feature.ProductListViewModel
import ru.otus.marketsample.products.feature.ProductsScreen
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.promo.feature.PromosScreen

sealed class ScreenRoute(val route: String, val title: String, val icon: ImageVector) {
    object Products: ScreenRoute("products_screen", "Products", Icons.Default.Home)
    object Promos: ScreenRoute("promos_screen", "Promos", Icons.Default.Settings)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                MainNavigation()
            }
        }
    }
}