package ru.otus.marketsample.products.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import ru.otus.marketsample.MarketSampleApp
import ru.otus.marketsample.R
import ru.otus.marketsample.products.feature.di.DaggerProductListComponent
import javax.inject.Inject

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
            setContent {
                ProductListScreen(viewModel,  requireActivity().findNavController(R.id.nav_host_activity_main))
            }
        }
    }


}
