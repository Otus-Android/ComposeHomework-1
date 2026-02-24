package ru.otus.marketsample.details.feature

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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import kotlinx.coroutines.launch
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.details.feature.di.DaggerDetailsComponent
import ru.otus.marketsample.R
import ru.otus.marketsample.databinding.FragmentDetailsBinding
import ru.otus.marketsample.details.feature.ui.DetailsScreen
import javax.inject.Inject

class DetailsFragment : Fragment() {

    @Inject
    lateinit var factory: DetailsViewModelFactory

    private val viewModel: DetailsViewModel by viewModels(
        factoryProducer = { factory }
    )

    private val productId by lazy { arguments?.getString("productId")!! }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        DaggerDetailsComponent.factory()
            .create(
                dependencies = findDependencies(),
                productId = productId,
            )
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

                DetailsScreen(
                    state = state,
                    onAddToCartClick = {
                        Toast.makeText(context, "Added to cart!", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

}
