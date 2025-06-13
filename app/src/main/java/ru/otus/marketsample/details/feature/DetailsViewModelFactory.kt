package ru.otus.marketsample.details.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import ru.otus.marketsample.details.domain.ConsumeProductDetailsUseCase
import ru.otus.common.di.FeatureScope
import javax.inject.Inject
import javax.inject.Named

@FeatureScope
class DetailsViewModelFactory @Inject constructor(
    private val consumeProductDetailsUseCase: ConsumeProductDetailsUseCase,
    private val detailsStateFactory: DetailsStateFactory,
    @Named("productId")
    private val productId: String,
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras,
    ): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                ?: throw IllegalStateException("Application is null in ViewModelProvider extras")

            @Suppress("UNCHECKED_CAST")
            return DetailsViewModel(
                consumeProductDetailsUseCase = consumeProductDetailsUseCase,
                detailsStateFactory = detailsStateFactory,
                productId = productId,
                application = application
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
