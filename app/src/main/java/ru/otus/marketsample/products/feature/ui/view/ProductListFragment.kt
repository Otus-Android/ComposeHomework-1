package ru.otus.marketsample.products.feature.ui.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import ru.otus.marketsample.products.feature.ui.ProductListViewModel
import ru.otus.marketsample.products.feature.ui.ProductListViewModelFactory
import ru.otus.marketsample.products.feature.ui.compose.ProductListRoute
import javax.inject.Inject
import ru.otus.marketsample.R

class ProductListFragment : Fragment() {

    @Inject
    lateinit var factory: ProductListViewModelFactory

    private val viewModel: ProductListViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        val appComponent = (activity?.applicationContext as MarketSampleApp).appComponent

        DaggerProductListComponent.factory()
            .create(appComponent)
            .inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Set the Compose content
            setContent {
                ProductListRoute(viewModel = viewModel, navigateToDetails = ::navigateToDetails)
            }
        }
    }

    private fun navigateToDetails(productId: String) {
        requireActivity().findNavController(R.id.nav_host_activity_main)
            .navigate(
                resId = R.id.action_main_to_details,
                args = bundleOf("productId" to productId),
            )
    }
}
