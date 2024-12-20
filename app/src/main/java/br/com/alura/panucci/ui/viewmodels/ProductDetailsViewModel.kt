package br.com.alura.panucci.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alura.panucci.dao.ProductDao
import br.com.alura.panucci.ui.uistate.ProductDetailsUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import kotlin.random.Random

class ProductDetailsViewModel(
    private val dao: ProductDao = ProductDao()
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun findProductById(id: String, promoCode: String? = null) {
            _uiState.update { ProductDetailsUiState.Loading }
            viewModelScope.launch {
                val timeMillis = Random.nextLong(500, 2000)
                delay(timeMillis)

                val dataState = dao.findById(id)?.let { product ->
                    val discount = when (promoCode) {
                        "ALURA" -> BigDecimal("0.1")
                        else -> BigDecimal.ZERO
                    }
                    val currentPrice = product.price
                    ProductDetailsUiState.Success(product.copy(price = currentPrice - (currentPrice * discount)))
                } ?: ProductDetailsUiState.Failure

                _uiState.update { dataState }
            }
    }

}