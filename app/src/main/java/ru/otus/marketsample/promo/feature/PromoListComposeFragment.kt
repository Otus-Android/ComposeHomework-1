package ru.otus.marketsample.promo.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.promo.feature.adapter.PromoAdapter
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import javax.inject.Inject

class PromoListComposeFragment : Fragment() {
    @Inject
    lateinit var adapter: PromoAdapter

    @Inject
    lateinit var factory: PromoListViewModelFactory

    private val viewModel: PromoListViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerPromoComponent.factory()
            .create(dependencies = findDependencies())
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
                    val state = viewModel.state.collectAsState().value
                    PromoListComposeScreen(state = state, onRefresh = { viewModel.refresh() })
                }
            }
        }
    }
}