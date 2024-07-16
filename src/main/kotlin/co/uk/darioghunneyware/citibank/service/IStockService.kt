package co.uk.darioghunneyware.citibank.service

import co.uk.darioghunneyware.citibank.model.enumeration.Indicator
import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import java.math.BigDecimal

interface IStockService {
    fun add(
        stockIdentifier: String,
        type: StockType,
        lastDividend: BigDecimal,
        parValue: BigDecimal,
        fixedDividend: BigDecimal?,
    )

    fun remove(stockIdentifier: String)

    fun displayStocks(): String

    fun displayTrades(): String

    fun calculateDividendYield(
        stockIdentifier: String,
        price: BigDecimal,
    ): BigDecimal

    fun calculatePriceToEarningsRatio(
        stockIdentifier: String,
        price: BigDecimal,
    ): BigDecimal

    fun captureTrade(
        stockIdentifier: String,
        indicator: Indicator,
        quantity: Int,
        tradedPrice: BigDecimal,
    )

    fun calculateVolumeWeightedStockPrice(): BigDecimal

    fun calculateShareIndex(): BigDecimal
}
