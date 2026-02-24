package ru.otus.marketsample.promo.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import ru.otus.marketsample.promo.feature.ui.PromoListScreen
import javax.inject.Inject

class PromoListFragment : Fragment() {

    @Inject
    lateinit var factory: PromoListViewModelFactory

    private val viewModel: PromoListViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerPromoComponent.factory()
            .create(dependencies = findDependencies())
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val state by viewModel.state.collectAsState()

                if (state.hasError) {
                    Toast.makeText(context, "Error while loading data", Toast.LENGTH_SHORT).show()
                    viewModel.errorHasShown()
                }

                PromoListScreen(
                    state = state,
                    onRefresh = { viewModel.refresh() }
                )
            }
        }
    }

}
