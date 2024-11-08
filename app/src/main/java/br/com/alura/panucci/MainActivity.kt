package br.com.alura.panucci

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.alura.panucci.navigation.AppDestination
import br.com.alura.panucci.navigation.bottomAppBarItems
import br.com.alura.panucci.preferences.dataStore
import br.com.alura.panucci.preferences.userPreferences
import br.com.alura.panucci.sampledata.sampleProductWithImage
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.components.BottomAppBarItem
import br.com.alura.panucci.ui.components.PanucciBottomAppBar
import br.com.alura.panucci.ui.screens.*
import br.com.alura.panucci.ui.theme.PanucciTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            LaunchedEffect(Unit) {
                navController.addOnDestinationChangedListener { _, _, _ ->
                    val routes = navController.backQueue.map {
                        it.destination.route
                    }
                    Log.i("BackQueue", "onCreate: routes=$routes")
                }
            }

            val backStackEntryState by navController.currentBackStackEntryAsState()
            val currentDestination = backStackEntryState?.destination
            PanucciTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val selectedItem by remember(currentDestination) {
                        val item = currentDestination?.let { destination ->
                            bottomAppBarItems.find { it.destination.route == destination.route }
                        } ?: bottomAppBarItems.first()
                        mutableStateOf(item)
                    }
                    val containsInBottomAppBarItems = currentDestination?.let { destination ->
                        bottomAppBarItems.find {
                            it.destination.route == destination.route
                        }
                    } != null
                    val isShowFab = when (currentDestination?.route) {
                        AppDestination.Menu.route,
                            AppDestination.Drinks.route -> true
                        else -> false
                    }
                    PanucciApp(bottomAppBarItemSelected = selectedItem,
                        isShowTopBar = containsInBottomAppBarItems,
                        isShowBottomBar = containsInBottomAppBarItems,
                        isShowFab = isShowFab,
                        onBottomAppBarItemSelectedChange = {
                            navController.navigate(it.destination.route) {
                                launchSingleTop = true
                                popUpTo(it.destination.route)
                            }
                        },
                        onFabClick = {
                            navController.navigate(AppDestination.Checkout.route) {
                                launchSingleTop = true
                            }
                        },
                        onLogout = {
                            scope.launch {
                                context.dataStore.edit {
                                    it.remove(userPreferences)
                                }
                            }
                            navController.navigate(AppDestination.Authentication.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                            }
                        }
                    ) {
                        NavHost(navController = navController,
                            startDestination = AppDestination.Highlight.route
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
                            composable(AppDestination.Highlight.route) {

                                var user: String? by remember {
                                    mutableStateOf(null)
                                }
                                var dataState by remember { mutableStateOf("loading") }
                                LaunchedEffect(null) {
                                    val randomMillis = Random.nextLong(500, 1000)
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
                                                    navController.navigate(
                                                        "${AppDestination.ProductDetails.route}/${product.id}?promoCode=${promoCode}"
                                                    )
                                                },
                                                onNavigateToCheckout = {
                                                    navController.navigate(AppDestination.Checkout.route)
                                                }
                                            )
                                        } ?: LaunchedEffect(Unit) {
                                            navController.navigate(AppDestination.Authentication.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            composable(AppDestination.Menu.route) {
                                MenuListScreen(
                                    products = sampleProducts,
                                    onNavigateToDetails = { product ->
                                        navController.navigate("${AppDestination.ProductDetails.route}/${product.id}")
                                    },
                                )
                            }
                            composable(AppDestination.Drinks.route) {
                                DrinksListScreen(
                                    products = sampleProducts,
                                    onNavigateToDetails = { product ->
                                        navController.navigate("${AppDestination.ProductDetails.route}/${product.id}")
                                    }
                                )
                            }
                            composable(
                                route = "${AppDestination.ProductDetails.route}/{productId}?promoCode={promoCode}",
                                arguments = listOf(navArgument("promoCode") { nullable = true })
                                ) { backStackEntry ->

                                val promoCode = backStackEntry.arguments?.getString("promoCode")
                                backStackEntry.arguments?.getString("productId")?.let { productId ->
                                    sampleProducts.find { it.id == productId }?.let { product ->
                                        val discount = when(promoCode) {
                                            "ALURA" -> BigDecimal("0.1")
                                            else -> BigDecimal.ZERO
                                        }
                                        val currentPrice = product.price
                                        ProductDetailsScreen(
                                            product = product.copy(price = currentPrice - (currentPrice*discount)),
                                            onNavigateToCheckout = { product ->
                                                navController.navigate(AppDestination.Checkout.route)
                                            }
                                        )
                                    } ?: LaunchedEffect(Unit) { navController.navigateUp() }
                                }
                            }
                            composable(AppDestination.Checkout.route) {
                                CheckoutScreen(products = sampleProducts, onPopBackStack = {
                                    navController.navigateUp()
                                })
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PanucciApp(
    bottomAppBarItemSelected: BottomAppBarItem = bottomAppBarItems.first(),
    onBottomAppBarItemSelectedChange: (BottomAppBarItem) -> Unit = {},
    onFabClick: () -> Unit = {},
    onLogout: () -> Unit = {},
    isShowTopBar: Boolean = false,
    isShowBottomBar: Boolean = false,
    isShowFab: Boolean = false,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            if (isShowTopBar) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Ristorante Panucci")
                    },
                    actions = {
                        IconButton(onClick = onLogout) {
                            Icon(
                                Icons.Filled.ExitToApp, contentDescription = "Sair do app"
                            )
                        }
                    }
                )
            }
    }, bottomBar = {
        if (isShowBottomBar) {
            PanucciBottomAppBar(
                item = bottomAppBarItemSelected,
                items = bottomAppBarItems,
                onItemChange = onBottomAppBarItemSelectedChange,
            )
        }
    }, floatingActionButton = {
        if (isShowFab) {
            FloatingActionButton(
                onClick = onFabClick
            ) {
                Icon(
                    Icons.Filled.PointOfSale, contentDescription = null
                )
            }
        }
    }) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun PanucciAppPreview() {
    PanucciTheme {
        Surface {
            PanucciApp {}
        }
    }
}