package br.com.alura.panucci.navigation

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.preferences.dataStore
import br.com.alura.panucci.preferences.userPreferences
import br.com.alura.panucci.ui.screens.AuthenticationScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun NavGraphBuilder.authenticationScreen(
    scope: CoroutineScope,
    context: Context,
    navController: NavHostController
) {
    composable(AppDestination.Authentication.route) {
        AuthenticationScreen(
            onEnterClick = { user ->
                scope.launch {
                    context.dataStore.edit {
                        it[userPreferences] = user
                    }
                }

                navController.navigate(AppDestination.Highlight.route) {
                    popUpTo(navController.graph.id)
                }
            }
        )
    }
}