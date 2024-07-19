package co.uk.darioghunneyware.citibank.model.factory

import co.uk.darioghunneyware.citibank.model.Stock
import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal

interface IStockFactory {
    fun createStock(
        stockIdentifier: String,
        type: StockType,
        lastDividend: BigDecimal,
        parValue: BigDecimal,
        fixedDividend: BigDecimal?,
    ): Stock
}