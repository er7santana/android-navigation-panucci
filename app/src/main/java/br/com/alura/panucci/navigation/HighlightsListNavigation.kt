package br.com.alura.panucci.navigation

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.panucci.preferences.dataStore
import br.com.alura.panucci.preferences.userPreferences
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.HighlightsListScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlin.random.Random

internal const val highlightsListRoute = "highlight"

fun NavGraphBuilder.highlightsListScreen(
    context: Context,
    navController: NavHostController
) {
    composable(highlightsListRoute) {

        var user: String? by remember {
            mutableStateOf(null)
        }
        var dataState by remember { mutableStateOf("loading") }
        LaunchedEffect(null) {
            val randomMillis = Random.nextLong(100, 200)
            delay(randomMillis)
            user = context.dataStore.data.first()[userPreferences]
            dataState = "finished"
        }
        when (dataState) {
            "loading" -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }

            else -> {
                user?.let {
                    HighlightsListScreen(
                        products = sampleProducts,
                        onNavigateToDetails = { product ->
                            val promoCode = "ALURA"
                            navController.navigateToProductDetails(product.id, promoCode)
                        },
                        onNavigateToCheckout = {
                            navController.navigateToCheckout()
                        }
                    )
                } ?: LaunchedEffect(Unit) {
                    navController.navigateToAuthentication {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }
}

fun NavController.navigateToHighlightsList(navOptions: NavOptions? = null) {
    navigate(highlightsListRoute, navOptions)
}