package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import java.math.BigDecimal

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
    composable(
        route = "${AppDestination.ProductDetails.route}/{productId}?promoCode={promoCode}",
        arguments = listOf(navArgument("promoCode") { nullable = true })
    ) { backStackEntry ->

        val promoCode = backStackEntry.arguments?.getString("promoCode")
        backStackEntry.arguments?.getString("productId")?.let { productId ->
            sampleProducts.find { it.id == productId }?.let { product ->
                val discount = when (promoCode) {
                    "ALURA" -> BigDecimal("0.1")
                    else -> BigDecimal.ZERO
                }
                val currentPrice = product.price
                ProductDetailsScreen(
                    product = product.copy(price = currentPrice - (currentPrice * discount)),
                    onNavigateToCheckout = { product ->
                        navController.navigate(AppDestination.Checkout.route)
                    }
                )
            } ?: LaunchedEffect(Unit) { navController.navigateUp() }
        }
    }
}