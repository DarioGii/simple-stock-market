package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal
import java.math.RoundingMode

class Preferred(
    override val identifier: String,
    override val lastDividend: BigDecimal,
    override val parValue: BigDecimal,
    override val type: StockType = StockType.PREFERRED,
    val fixedDividend: BigDecimal,
) : Stock(identifier, lastDividend, parValue, type) {
    companion object {
        private const val SCALE: Int = 2
    }

    override fun calculateDividendYield(price: BigDecimal): BigDecimal =
        (fixedDividend.movePointLeft(SCALE) * parValue / price).setScale(SCALE, RoundingMode.HALF_UP)

    override fun toString(): String =
        """
        Stock Identifier: $identifier
        Type: $type
        Last Dividend: ${lastDividend.setScale(SCALE, RoundingMode.HALF_UP)}
        Fixed Dividend: ${fixedDividend.setScale(SCALE, RoundingMode.HALF_UP)}%
        Par Value: ${parValue.setScale(SCALE, RoundingMode.HALF_UP)}
        """.trimIndent()
}
