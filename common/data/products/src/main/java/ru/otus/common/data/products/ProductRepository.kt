package ru.otus.common.data.products

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productRemoteDataSource: ProductRemoteDataSource,
    private val productDataMapper: ProductDataMapper,
    private val dispatcher: CoroutineDispatcher,
) {
    private val scope = CoroutineScope(SupervisorJob() + dispatcher)

    fun consumeProducts(): Flow<List<ProductEntity>> {
        scope.launch(Dispatchers.IO) {
            val products = productRemoteDataSource.getProducts()
            productLocalDataSource.saveProducts(
                products.map(productDataMapper::toEntity)
            )
        }
        return productLocalDataSource.consumeProducts()
    }
}
