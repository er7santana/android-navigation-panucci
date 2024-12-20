package br.com.alura.panucci.sampledata

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import br.com.alura.panucci.model.Product
import java.math.BigDecimal
import java.util.UUID
import kotlin.random.Random

private val loremName = LoremIpsum(Random.nextInt(10)).values.first()
private val loremDesc = LoremIpsum(Random.nextInt(30)).values.first()

val sampleProductWithImage = Product(
    name = LoremIpsum(10).values.first(),
    price = BigDecimal("9.99"),
    description = LoremIpsum(30).values.first(),
    image = "https://picsum.photos/1920/1080",
    id = UUID.randomUUID().toString()
)

val sampleProductWithoutImage = Product(
    name = LoremIpsum(10).values.first(),
    price = BigDecimal("9.99"),
    description = LoremIpsum(30).values.first(),
    id = UUID.randomUUID().toString(),
)

val sampleProducts = List(10) { index ->
    Product(
        name = loremName,
        price = BigDecimal("9.99"),
        description = loremDesc,
        image = if (index % 2 == 0) "https://picsum.photos/1920/1080" else null,
        id = UUID.randomUUID().toString()
    )
}