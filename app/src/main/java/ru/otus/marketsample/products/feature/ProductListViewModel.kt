package ru.otus.marketsample.products.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.otus.marketsample.R
import ru.otus.marketsample.products.domain.ConsumeProductsUseCase
import javax.inject.Inject

class ProductListViewModel @Inject constructor(
    private val consumeProductsUseCase: ConsumeProductsUseCase,
    private val productStateFactory: ProductStateFactory,
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state: StateFlow<ProductsScreenState> = _state.asStateFlow()

    init {
        requestProducts()
    }

    private fun requestProducts() {
        consumeProductsUseCase().map { products ->
            products.map { product -> productStateFactory.create(product) }
        }
            .onStart {
                _state.update { screenState -> screenState.copy(isLoading = true) }
            }
            .onEach { productListState ->
                _state.update { screenState ->
                    screenState.copy(
                        isLoading = false,
                        productListState = productListState,
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
        requestProducts()
    }

    fun errorHasShown() {
        _state.update { screenState -> screenState.copy(hasError = false) }
    }
}
