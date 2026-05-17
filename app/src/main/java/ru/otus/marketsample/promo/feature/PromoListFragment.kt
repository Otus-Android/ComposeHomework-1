package ru.otus.marketsample.promo.feature

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.otus.common.di.findDependencies
import ru.otus.marketsample.common.Loader
import ru.otus.marketsample.databinding.FragmentPromoListBinding
import ru.otus.marketsample.products.feature.compose.PromoList
import ru.otus.marketsample.promo.feature.adapter.PromoAdapter
import ru.otus.marketsample.promo.feature.di.DaggerPromoComponent
import javax.inject.Inject

class PromoListFragment : Fragment() {

    private var _binding: FragmentPromoListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: PromoAdapter

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
        _binding = FragmentPromoListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.promoComposeView.setContent {
            val state by viewModel.state.collectAsState()

            when {
                state.isLoading -> Loader()
                state.hasError -> Toast.makeText(
                    requireContext(),
                    "Error wile loading data",
                    Toast.LENGTH_SHORT
                ).show()
                else -> PromoList(state, onRefresh = { viewModel.refresh()})
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
