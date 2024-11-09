package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import java.math.BigDecimal

private const val productDetailsRoute = "productDetails"
private const val productIdArgument = "productId"
private const val promoCodeArgument = "promoCode"

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
    composable(
        route = "$productDetailsRoute/{$productIdArgument}?$promoCodeArgument={$promoCodeArgument}",
        arguments = listOf(navArgument(promoCodeArgument) { nullable = true })
    ) { backStackEntry ->

        val promoCode = backStackEntry.arguments?.getString(promoCodeArgument)
        backStackEntry.arguments?.getString(productIdArgument)?.let { productId ->
            sampleProducts.find { it.id == productId }?.let { product ->
                val discount = when (promoCode) {
                    "ALURA" -> BigDecimal("0.1")
                    else -> BigDecimal.ZERO
                }
                val currentPrice = product.price
                ProductDetailsScreen(
                    product = product.copy(price = currentPrice - (currentPrice * discount)),
                    onNavigateToCheckout = { product ->
                        navController.navigateToCheckout()
                    }
                )
            } ?: LaunchedEffect(Unit) { navController.navigateUp() }
        }
    }
}

fun NavHostController.navigateToProductDetails(productId: String, promoCode: String? = null, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate("$productDetailsRoute/$productId?$promoCodeArgument=$promoCode") {
        builder()
    }
}