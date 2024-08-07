package co.uk.darioghunneyware.citibank.service.impl

import co.uk.darioghunneyware.citibank.exception.StockNotFoundException
import co.uk.darioghunneyware.citibank.exception.TradeException
import co.uk.darioghunneyware.citibank.model.Ordinary
import co.uk.darioghunneyware.citibank.model.Preferred
import co.uk.darioghunneyware.citibank.model.Stock
import co.uk.darioghunneyware.citibank.model.Trade
import co.uk.darioghunneyware.citibank.model.enumeration.Indicator
import co.uk.darioghunneyware.citibank.model.enumeration.StockType
import co.uk.darioghunneyware.citibank.service.IStockService
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import kotlin.math.pow

@Service
@ShellComponent
class StockService(
    private val stocks: MutableSet<Stock>,
    private val trades: MutableMap<String, MutableList<Trade>>,
) : IStockService {
    companion object {
        private const val SCALE = 2
        private const val ONE: Double = 1.0
    }

    @ShellMethod(key = ["add"], value = "Add a Stock")
    override fun add(
        stockIdentifier: String,
        type: StockType,
        lastDividend: BigDecimal,
        parValue: BigDecimal,
        fixedDividend: BigDecimal?,
    ) {
        val stock =
            when (type) {
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

        stocks.add(stock)
    }

    @ShellMethod(key = ["remove"], value = "Remove a Stock")
    override fun remove(stockIdentifier: String) {
        val stock = getStock(stockIdentifier)

        if (!stocks.remove(stock)) {
            throw StockNotFoundException("Stock $stockIdentifier not found.")
        }
    }

    @ShellMethod(key = ["calculate-dividend-yield"], value = "Calculate the dividend yield for a given Stock")
    override fun calculateDividendYield(
        stockIdentifier: String,
        price: BigDecimal,
    ): BigDecimal {
        val stock = getStock(stockIdentifier) ?: throw StockNotFoundException("Stock $stockIdentifier not found.")

        return stock.calculateDividendYield(price)
    }

    @ShellMethod(key = ["calculate-pe-ratio"], value = "Calculate the Price to Earnings Ratio for a given Stock")
    override fun calculatePriceToEarningsRatio(
        stockIdentifier: String,
        price: BigDecimal,
    ): BigDecimal {
        val stock = getStock(stockIdentifier) ?: throw StockNotFoundException("Stock $stockIdentifier not found.")

        return stock.calculatePriceToEarningsRatio(price)
    }

    @ShellMethod(key = ["capture-trade"], value = "Capture a Trade")
    override fun captureTrade(
        stockIdentifier: String,
        indicator: Indicator,
        quantity: Int,
        tradedPrice: BigDecimal,
    ) {
        val stock = getStock(stockIdentifier) ?: throw StockNotFoundException("Stock $stockIdentifier not found.")
        val trade = Trade(stock, LocalDateTime.now(), quantity, indicator, tradedPrice)

        if (trades.containsKey(stockIdentifier)) {
            trades[stockIdentifier]?.add(trade)
        } else {
            trades[stockIdentifier] = mutableListOf(trade)
        }
    }

    @ShellMethod(key = ["calculate-vwap"], value = "Calculate the Volume Weighted Stock Price based on trades in past 15 minutes")
    override suspend fun calculateVolumeWeightedStockPrice(): BigDecimal {
        if (trades.isEmpty()) {
            throw TradeException("No trades have been executed yet.")
        }

        val vwap =
            coroutineScope {
                async(CoroutineName("VWAP Coroutine")) {
                    val now = LocalDateTime.now()
                    val lastFifteenMinutes = now.minusMinutes(15)

                    val recentTrades =
                        trades.values.flatMap { tradeList ->
                            tradeList.filter {
                                it.timestamp.isBefore(now) &&
                                    it.timestamp.isAfter(lastFifteenMinutes)
                            }
                        }

                    recentTrades
                        .sumOf { it.tradedPrice.times(BigDecimal(it.quantity)) }
                        .div(BigDecimal(recentTrades.size))
                        .setScale(SCALE, RoundingMode.HALF_UP)
                }
            }

        return vwap.await()
    }

    @ShellMethod(key = ["calculate-all-share-index"], value = "Calculate the all Share Index for all Stocks")
    override suspend fun calculateShareIndex(): BigDecimal {
        val allShareIndex =
            coroutineScope {
                async(CoroutineName("All Share Index Coroutine")) {
                    try {
                        val product =
                            trades.values
                                .map { tradeList ->
                                    tradeList.map { it.tradedPrice }
                                }.flatten()
                                .reduce { acc, trade -> acc.times(trade) }

                        product
                            .toDouble()
                            .pow(ONE.div(trades.size))
                            .toBigDecimal()
                            .setScale(SCALE, RoundingMode.HALF_UP)
                    } catch (uoe: UnsupportedOperationException) {
                        throw TradeException("No trades have been executed yet.")
                    }
                }
            }

        return allShareIndex.await()
    }

    @ShellMethod(key = ["display-stocks"], value = "Displays all Stocks")
    override fun displayStocks(): String {
        var display = ""

        stocks.forEach {
            display += it.toString().plus("\n\n")
        }

        return display.trim()
    }

    @ShellMethod(key = ["display-trades"], value = "Displays all Trades, grouped by Stock")
    override fun displayTrades(): String {
        var display = ""

        trades.forEach { (identifier, tradeList) ->
            display +=
                identifier
                    .plus("\n\n")
                    .plus(tradeList)
                    .plus("\n\n")
        }

        return display.trim()
    }

    private fun getStock(stockIdentifier: String) = stocks.find { stockIdentifier == it.identifier }
}
