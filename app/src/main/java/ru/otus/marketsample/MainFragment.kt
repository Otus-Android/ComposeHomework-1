package ru.otus.marketsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.otus.marketsample.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navView: BottomNavigationView = binding.navView

        navView.setOnItemSelectedListener {
            findNavController(binding.navHostFragmentMain).navigate(it.itemId)
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

//@Serializable
//sealed class Screen(
//    @StringRes val title: Int,
//    @DrawableRes val icon: Int
//) {
//    @Serializable
//    object ProductList : Screen(
//        title = R.string.title_products,
//        icon = ru.otus.common.ui.R.drawable.ic_list
//    )
//
//    @Serializable
//    object PromoList : Screen(
//        title = R.string.title_promo,
//        icon = ru.otus.common.ui.R.drawable.ic_discount
//    )
//
//    @Serializable
//    data class ProductDetails(val id: String)
//}
//
//@Composable
//fun MainScreen() {
//    val navController = rememberNavController()
//    val bottomNavigationItems = listOf(ProductList, PromoList)
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentRoute = navBackStackEntry?.destination
//
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                bottomNavigationItems.forEach { navigationItem ->
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                painter = painterResource(navigationItem.icon),
//                                contentDescription = null
//                            )
//                        },
//                        label = {
//                            Text(stringResource(navigationItem.title))
//                        },
//                        selected = currentRoute == navigationItem,
//                        onClick = {
//                            navController.navigate(navigationItem)
//                        }
//                    )
//                }
//            }
//        },
//    ) { padding ->
//        NavHost(
//            navController,
//            startDestination = ProductList,
//            modifier = Modifier.padding(padding)
//        ) {
//            composable<ProductList> {
//                ProductListScreen(
//                    navigateToDetails = { productId ->
//                        navController.navigate(
//                            route = ProductDetails(productId)
//                        )
//                    }
//                )
//            }
//            composable<PromoList> {
//                PromoListScreen()
//            }
//            composable<ProductDetails> { backStackEntry ->
//                val productId: String = backStackEntry.toRoute()
//                DetailsScreen(productId = productId)
//            }
//        }
//    }
//}