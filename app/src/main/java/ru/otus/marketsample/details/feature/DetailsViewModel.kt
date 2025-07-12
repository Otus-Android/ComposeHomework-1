package ru.otus.marketsample.details.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import ru.otus.marketsample.R
import ru.otus.marketsample.details.domain.ConsumeProductDetailsUseCase
import javax.inject.Inject
import javax.inject.Named

class DetailsViewModel @Inject constructor(
    private val consumeProductDetailsUseCase: ConsumeProductDetailsUseCase,
    private val detailsStateFactory: DetailsStateFactory,
    @Named("productId") private val productId: String,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsScreenState())
    val state: StateFlow<DetailsScreenState> = _state.asStateFlow()

    init {
        requestProducts()
    }

    private fun requestProducts() {
        consumeProductDetailsUseCase(productId)
            .map { productDetails -> detailsStateFactory.create(productDetails) }
            .flowOn(Dispatchers.IO)
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { detailsState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        detailsState = detailsState,
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

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }
}
