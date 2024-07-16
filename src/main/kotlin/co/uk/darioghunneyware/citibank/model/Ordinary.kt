package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal

data class Ordinary(
    override val identifier: String,
    override val lastDividend: BigDecimal,
    override val parValue: BigDecimal,
    override val type: StockType = StockType.ORDINARY,
) : Stock(identifier, lastDividend, parValue, type) {
    override fun calculateDividendYield(price: BigDecimal): BigDecimal = (lastDividend / price).setScale(2)

    override fun toString(): String =
        """
        Stock Identifier: $identifier
        Type: $type
        Last Dividend: $lastDividend
        Par Value: $parValue
        """.trimIndent()
}
