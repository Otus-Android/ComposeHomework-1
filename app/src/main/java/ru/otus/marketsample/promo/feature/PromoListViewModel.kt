package ru.otus.marketsample.promo.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.otus.marketsample.R
import ru.otus.marketsample.promo.domain.ConsumePromosUseCase
import javax.inject.Inject

class PromoListViewModel @Inject constructor(
    private val promoStateFactory: PromoStateFactory,
    private val consumePromosUseCase: ConsumePromosUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(PromoScreenState())
    val state: StateFlow<PromoScreenState> = _state.asStateFlow()

    init {
        requestPromos()
    }

    private fun requestPromos() {
        consumePromosUseCase()
            .map { promos ->
                promos.map(promoStateFactory::map)
            }
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { promoListState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        promoListState = promoListState,
                    )
                }
            }
            .catch {
                _state.update { screenState ->
                    screenState.copy(
                        hasError = true,
                        errorProvider = { context -> context.getString(R.string.error_wile_loading_data) }
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    fun refresh() {
        requestPromos()
    }

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }
}
