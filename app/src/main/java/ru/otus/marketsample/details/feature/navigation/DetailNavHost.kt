package ru.otus.marketsample.details.feature.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import ru.otus.marketsample.details.feature.DetailsFragment

fun NavGraphBuilder.detailNavHost(navHostController: NavHostController) {
    composable<FragmentHost>(
        enterTransition = { scaleIn() + fadeIn() },
        exitTransition = { scaleOut() + fadeOut() }
    ) {backstack ->
        val id = backstack.arguments?.getString("productId")
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AndroidFragment<DetailsFragment>(
                modifier = Modifier
                    .fillMaxSize(),
                arguments = bundleOf("productId" to id)
            )
        }
    }
}

@Serializable
data class FragmentHost(val productId: String)