package ru.otus.marketsample.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class BottomNavigationItem(
    val route: String,
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
)