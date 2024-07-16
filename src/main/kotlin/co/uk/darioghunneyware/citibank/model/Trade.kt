package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.Indicator
import java.math.BigDecimal
import java.time.LocalDateTime

data class Trade(
    val stock: Stock,
    val timestamp: LocalDateTime,
    val quantity: Int,
    val indicator: Indicator,
    val tradedPrice: BigDecimal,
) {
    override fun toString(): String =
        """
        
        Timestamp: $timestamp
        Quantity: $quantity
        Buy/Sell: $indicator
        Traded Price: $tradedPrice
        
        """.trimIndent()
}
