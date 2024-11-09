package br.com.alura.panucci.navigation

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import br.com.alura.panucci.preferences.dataStore
import br.com.alura.panucci.preferences.userPreferences
import br.com.alura.panucci.ui.screens.AuthenticationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal const val authenticationRoute = "authentication"

fun NavGraphBuilder.authenticationScreen(
    scope: CoroutineScope,
    context: Context,
    navController: NavHostController
) {
    composable(authenticationRoute) {
        AuthenticationScreen(
            onEnterClick = { user ->
                scope.launch {
                    context.dataStore.edit {
                        it[userPreferences] = user
                    }
                }

                val navOptions = navOptions {
                    popUpTo(navController.graph.id)
                }
                navController.navigateToHighlightsList(navOptions)
            }
        )
    }
}

fun NavController.navigateToAuthentication(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(authenticationRoute) {
        builder()
    }
}