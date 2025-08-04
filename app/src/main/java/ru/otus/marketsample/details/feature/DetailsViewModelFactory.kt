package ru.otus.marketsample.details.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.otus.marketsample.details.domain.ConsumeProductDetailsUseCase
import javax.inject.Inject

class DetailsViewModelFactory @Inject constructor(
    private val consumeProductDetailsUseCase: ConsumeProductDetailsUseCase,
    private val detailsStateFactory: DetailsStateFactory
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        when {
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(
                    consumeProductDetailsUseCase = consumeProductDetailsUseCase,
                    detailsStateFactory = detailsStateFactory
                ) as T
            }
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
