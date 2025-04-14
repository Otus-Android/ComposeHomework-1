package ru.otus.marketsample.promo.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import ru.otus.marketsample.promo.domain.ConsumePromosUseCase
import ru.otus.marketsample.R

class PromoListViewModel(
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

                if (state.value.promoListState.isEmpty())
                    _state.update { screenState -> screenState.copy(isLoading = true) }
                else
                    _state.update { screenState -> screenState.copy(isRefreshing = true) }
            }
            .onEach { promoListState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        promoListState = promoListState,
                    )
                }
                delay(500)
                _state.update { screenState ->
                    screenState.copy(
                        isRefreshing = false,
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
