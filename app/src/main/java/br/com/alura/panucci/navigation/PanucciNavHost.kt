package br.com.alura.panucci.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.CoroutineScope

@Composable
fun PanucciNavHost(navController: NavHostController, scope: CoroutineScope, context: Context) {
    NavHost(navController = navController,
        startDestination = AppDestination.Highlight.route
    ) {
        authenticationScreen(scope, context, navController)
        highlightsListScreen(context, navController)
        menuScreen(navController)
        drinksScreen(navController)
        productDetailsScreen(navController)
        checkoutScreen(navController)
    }
}







