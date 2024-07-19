package co.uk.darioghunneyware.citibank.model.factory.impl

import co.uk.darioghunneyware.citibank.model.Ordinary
import co.uk.darioghunneyware.citibank.model.Preferred
import co.uk.darioghunneyware.citibank.model.Stock
import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import co.uk.darioghunneyware.citibank.model.factory.IStockFactory
import java.math.BigDecimal

class StockFactory : IStockFactory {
    override fun createStock(
        stockIdentifier: String,
        type: StockType,
        lastDividend: BigDecimal,
        parValue: BigDecimal,
        fixedDividend: BigDecimal?,
    ): Stock = when (type) {
        StockType.ORDINARY ->
            Ordinary(
                identifier = stockIdentifier,
                lastDividend = lastDividend,
                parValue = parValue,
            )

        StockType.PREFERRED -> {
            if (fixedDividend == null) {
                throw IllegalArgumentException("Fixed Dividend is required for Preferred Shares.")
            }

            Preferred(
                identifier = stockIdentifier,
                lastDividend = lastDividend,
                fixedDividend = fixedDividend,
                parValue = parValue,
            )
        }
    }
}