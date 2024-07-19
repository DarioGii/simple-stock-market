package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import co.uk.darioghunneyware.citibank.util.Constants.Companion.SCALE
import java.math.BigDecimal
import java.math.RoundingMode

data class Ordinary(
    override val identifier: String,
    override val lastDividend: BigDecimal,
    override val parValue: BigDecimal,
    override val type: StockType = StockType.ORDINARY,
) : Stock(identifier, lastDividend, parValue, type) {
    override fun calculateDividendYield(price: BigDecimal): BigDecimal =
        (lastDividend / price)
            .setScale(SCALE, RoundingMode.HALF_UP)

    override fun toString(): String =
        """
        Stock Identifier: $identifier
        Type: $type
        Last Dividend: ${lastDividend.setScale(SCALE, RoundingMode.HALF_UP)}
        Par Value: ${parValue.setScale(SCALE, RoundingMode.HALF_UP)}
        """.trimIndent()
}
