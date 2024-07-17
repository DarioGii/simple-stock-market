package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal
import java.math.RoundingMode

abstract class Stock(
    open val identifier: String,
    open val lastDividend: BigDecimal,
    open val parValue: BigDecimal,
    open val type: StockType,
) {
    companion object {
        private const val SCALE = 2
    }

    abstract fun calculateDividendYield(price: BigDecimal): BigDecimal

    fun calculatePriceToEarningsRatio(price: BigDecimal): BigDecimal =
        price
            .div(lastDividend)
            .setScale(SCALE, RoundingMode.HALF_UP)
}
