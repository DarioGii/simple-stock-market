package co.uk.darioghunneyware.citibank.model

import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal

abstract class Stock(
    open val identifier: String,
    open val lastDividend: BigDecimal,
    open val parValue: BigDecimal,
    open val type: StockType,
) {
    abstract fun calculateDividendYield(price: BigDecimal): BigDecimal

    fun calculatePriceToEarningsRatio(price: BigDecimal): BigDecimal = price.div(lastDividend)
}
