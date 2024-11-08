package br.com.alura.panucci.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.outlined.LocalBar
import br.com.alura.panucci.ui.components.BottomAppBarItem

sealed class AppDestination(val route: String) {
    object Highlight : AppDestination("highlight")
    object Menu : AppDestination("menu")
    object Drinks : AppDestination("drinks")
    object ProductDetails : AppDestination("productDetails")
    object Checkout : AppDestination("checkout")

    object Cart : AppDestination("cart")
    object OrderConfirmation : AppDestination("orderConfirmation")
    object OrderHistory : AppDestination("orderHistory")
    object Profile : AppDestination("profile")
    object Settings : AppDestination("settings")
    object About : AppDestination("about")
    object Help : AppDestination("help")
    object Terms : AppDestination("terms")
    object Privacy : AppDestination("privacy")
    object Legal : AppDestination("legal")
    object Contact : AppDestination("contact")
    object Login : AppDestination("login")
    object Register : AppDestination("register")
    object ForgotPassword : AppDestination("forgotPassword")
    object ResetPassword : AppDestination("resetPassword")
    object VerifyEmail : AppDestination("verifyEmail")
    object VerifyPhone : AppDestination("verifyPhone")
    object VerifyIdentity : AppDestination("verifyIdentity")
    object VerifyAddress : AppDestination("verifyAddress")
    object VerifyPayment : AppDestination("verifyPayment")
    object VerifyOrder : AppDestination("verifyOrder")
    object VerifyReview : AppDestination("verifyReview")
    object VerifyFeedback : AppDestination("verifyFeedback")
    object VerifySupport : AppDestination("verifySupport")
    object VerifyLegal : AppDestination("verifyLegal")
    object VerifyTerms : AppDestination("verifyTerms")
    object VerifyPrivacy : AppDestination("verifyPrivacy")
    object VerifySecurity : AppDestination("verifySecurity")
    object VerifyProfile : AppDestination("verifyProfile")
    object VerifySettings : AppDestination("verifySettings")
    object VerifyAbout : AppDestination("verifyAbout")
    object VerifyHelp : AppDestination("verifyHelp")
    object VerifyContact : AppDestination("verifyContact")
    object VerifyCart : AppDestination("verifyCart")
    object VerifyCheckout : AppDestination("verifyCheckout")
    object VerifyOrderConfirmation : AppDestination("verifyOrderConfirmation")
    object VerifyOrderHistory : AppDestination("verifyOrderHistory")
    object VerifyProductDetails : AppDestination("verifyProductDetails")
    object VerifyMenu : AppDestination("verifyMenu")
    object VerifyDrinks : AppDestination("verifyDrinks")
    object VerifyHighlight : AppDestination("verifyHighlight")
    object VerifyLogin : AppDestination("verifyLogin")
}

val bottomAppBarItems = listOf(
    BottomAppBarItem(
        label = "Destaques",
        icon = Icons.Filled.AutoAwesome,
        destination = AppDestination.Highlight
    ),
    BottomAppBarItem(
        label = "Menu",
        icon = Icons.Filled.RestaurantMenu,
        destination = AppDestination.Menu
    ),
    BottomAppBarItem(
        label = "Bebidas",
        icon = Icons.Outlined.LocalBar,
        destination = AppDestination.Drinks
    ),
)