package br.com.alura.panucci.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.viewmodels.ProductDetailsViewModel
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

private const val productDetailsRoute = "productDetails"
private const val productIdArgument = "productId"
private const val promoCodeArgument = "promoCode"

fun NavGraphBuilder.productDetailsScreen(navController: NavHostController) {
    composable(
        route = "$productDetailsRoute/{$productIdArgument}?$promoCodeArgument={$promoCodeArgument}",
        arguments = listOf(navArgument(promoCodeArgument) { nullable = true })
    ) { backStackEntry ->

        backStackEntry.arguments?.getString(productIdArgument)?.let { productId ->
            val promoCode = backStackEntry.arguments?.getString(promoCodeArgument)

            val viewModel = viewModel<ProductDetailsViewModel>()
            val uiState by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.findProductById(productId, promoCode)
            }

            ProductDetailsScreen(
                uiState = uiState,
                onNavigateToCheckout = { product ->
//                    val discount = when (promoCode) {
//                        "ALURA" -> BigDecimal("0.1")
//                        else -> BigDecimal.ZERO
//                    }
//                    val currentPrice = product.price
//
//                    val productWithDiscount = product.copy(price = currentPrice - (currentPrice * discount))
                    navController.navigateToCheckout()
                },
            )
        } ?: LaunchedEffect(Unit) { navController.navigateUp() }
    }
}

fun NavHostController.navigateToProductDetails(productId: String, promoCode: String? = null, builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate("$productDetailsRoute/$productId?$promoCodeArgument=$promoCode") {
        builder()
    }
}