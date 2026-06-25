package ru.otus.marketsample

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import ru.otus.common.ui.R

@Composable
fun BottomBar(
    navController: NavController
){
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = {
                navController.navigate("products")
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_list),
                    contentDescription = "Products"
                )
            },
            label = {
                Text("Products")
            }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                navController.navigate("promo")
            },
            icon = {
                Icon(
                    painter = painterResource(R.drawable.ic_discount),
                    contentDescription = "Promo"
                )
            },
            label = {
                Text("Promo")
            }
        )
    }
}