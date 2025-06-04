package ru.otus.marketsample.products.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import javax.inject.Inject

class ProductListComposeFragment : Fragment() {

    @Inject
    lateinit var productListFactory: ProductListViewModelFactory

    private val productListViewModel: ProductListViewModel by viewModels { productListFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (activity?.applicationContext as MarketSampleApp).appComponent

        DaggerProductListComponent.factory()
            .create(appComponent)
            .injectComposeFrag(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    ProductScreenNavHost(
                        productListViewModel = productListViewModel
                    )
                }
            }
        }
    }
}