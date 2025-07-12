package ru.otus.marketsample.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.otus.marketsample.products.feature.ProductsScreenState

@Composable
fun MainApplicationScreen(
    modifier: Modifier = Modifier,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = bottomBar,
        modifier = modifier,
        content = content
    )
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    selected: String
) {
    NavigationBar(
        modifier = modifier,
    ) {
        NavigationBar {
            NavigationBarItem(
                selected =
            )
        }
    }
}

@Preview
@Composable
private fun PreviewBottomBar() {
    BottomBar()
}
