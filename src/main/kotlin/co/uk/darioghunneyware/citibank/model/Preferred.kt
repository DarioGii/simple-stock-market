package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal

class Preferred(
    override val identifier: String,
    override val lastDividend: BigDecimal,
    override val parValue: BigDecimal,
    override val type: StockType = StockType.PREFERRED,
    val fixedDividend: BigDecimal,
) : Stock(identifier, lastDividend, parValue, type) {
    companion object {
        private const val TWO: Int = 2
    }

    override fun calculateDividendYield(price: BigDecimal): BigDecimal = (fixedDividend.movePointLeft(TWO) * parValue / price)

    override fun toString(): String =
        """
        Stock Identifier: $identifier
        Type: $type
        Last Dividend: $lastDividend
        Fixed Dividend: $fixedDividend%
        Par Value: $parValue
        """.trimIndent()
}
