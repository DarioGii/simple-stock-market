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
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime
import java.util.stream.Stream

class StockServiceTest {
    companion object {
        val tea =
            Ordinary(
                identifier = "TEA",
                lastDividend = BigDecimal(100.46),
                parValue = BigDecimal(150.33),
            )
        val coffee =
            Preferred(
                identifier = "COF",
                lastDividend = BigDecimal(8.39),
                fixedDividend = BigDecimal(4.39),
                parValue = BigDecimal(100.39),
            )
        val milk =
            Ordinary(
                identifier = "MIL",
                lastDividend = BigDecimal(8.39),
                parValue = BigDecimal.valueOf(100.82),
            )
        val juice =
            Ordinary(
                identifier = "JUI",
                lastDividend = BigDecimal(23.64),
                parValue = BigDecimal.valueOf(70.39),
            )
        val water =
            Ordinary(
                identifier = "WAT",
                lastDividend = BigDecimal(13.39),
                parValue = BigDecimal.valueOf(250.23),
            )

        @JvmStatic
        fun dividendYieldParams(): Stream<Arguments> =
            Stream.of(
                Arguments.of("TEA", BigDecimal(75.8), BigDecimal(1.33).setScale(2, RoundingMode.HALF_UP)),
                Arguments.of("COF", BigDecimal(47.3), BigDecimal(0.09).setScale(2, RoundingMode.HALF_UP)),
            )
    }

    private val stocks = mutableListOf<Stock>()

    private val trades = mutableMapOf<String, MutableList<Trade>>()

    private lateinit var stockService: IStockService

    @BeforeEach
    fun setUp() {
        loadStocks()
        stockService = StockService(stocks, trades)
    }

    @Test
    fun `Add a Stock`() {
        val bis = "BIS"
        val biscuit =
            Ordinary(
                identifier = bis,
                lastDividend = BigDecimal(25),
                parValue = BigDecimal(100),
            )

        stockService.add(
            stockIdentifier = bis,
            type = StockType.ORDINARY,
            lastDividend = BigDecimal(25),
            parValue = BigDecimal(100),
            fixedDividend = null,
        )

        assertThat(stocks.size).isEqualTo(6)
        assertThat(stocks.last()).isEqualTo(biscuit)
    }

    @Test
    fun `Missing argument`() {
        assertThatThrownBy {
            stockService.add(
                stockIdentifier = "BIS",
                type = StockType.PREFERRED,
                lastDividend = BigDecimal(25),
                parValue = BigDecimal(100),
                fixedDividend = null,
            )
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Fixed Dividend is required for Preferred Shares.")
    }

    @Test
    fun `Remove a Stock`() {
        val bis = "BIS"
        val biscuit =
            Ordinary(
                identifier = bis,
                lastDividend = BigDecimal(25),
                parValue = BigDecimal(100),
            )
        val water =
            Ordinary(
                identifier = "WAT",
                lastDividend = BigDecimal(13.39),
                parValue = BigDecimal.valueOf(250.23),
            )

        stockService.add(
            stockIdentifier = bis,
            type = StockType.ORDINARY,
            lastDividend = BigDecimal(25),
            parValue = BigDecimal(100),
            fixedDividend = null,
        )

        assertThat(stocks.size).isEqualTo(6)
        assertThat(stocks.last()).isEqualTo(biscuit)

        stockService.remove(bis)

        assertThat(stocks.size).isEqualTo(5)
        assertThat(stocks.last()).isEqualTo(water)
    }

    @Test
    fun `Display all Stocks`() {
        val expected =
            """
$tea

$coffee

$milk

$juice

$water
            """.trimIndent()
        assertThat(stockService.displayStocks()).isEqualTo(expected)
    }

    @ParameterizedTest
    @MethodSource("dividendYieldParams")
    fun `Calculate dividend yield`(
        stockIdentifier: String,
        price: BigDecimal,
        value: BigDecimal,
    ) {
        assertThat(stockService.calculateDividendYield(stockIdentifier, price).setScale(2)).isEqualTo(value)
    }

    @Test
    fun `Exception thrown when stock not found for dividend yield`() {
        val id = "???"

        assertThatThrownBy { stockService.calculateDividendYield(id, BigDecimal(88)) }
            .isInstanceOf(StockNotFoundException::class.java)
            .hasMessage("Stock $id not found.")
    }

    @Test
    fun `Calculate price to earnings ratio`() {
        assertThat(stockService.calculatePriceToEarningsRatio("JUI", BigDecimal(146))).isEqualTo(BigDecimal(6.00).setScale(2))
    }

    @Test
    fun `Exception thrown when stock not found for pe ratio`() {
        val id = "???"

        assertThatThrownBy { stockService.calculatePriceToEarningsRatio(id, BigDecimal(24)) }
            .isInstanceOf(StockNotFoundException::class.java)
            .hasMessage("Stock $id not found.")
    }

    @Test
    fun `Capture trade`() {
        val id = "WAT"
        val quantity = 10
        val buy = Indicator.BUY
        val tradedPrice = BigDecimal(150)
        val water =
            Ordinary(
                identifier = id,
                lastDividend = BigDecimal(13.39),
                parValue = BigDecimal.valueOf(250.23),
            )
        val trade = Trade(water, LocalDateTime.now(), quantity, buy, tradedPrice)
        stockService.captureTrade(id, buy, quantity, tradedPrice)

        assertThat(trades.size).isOne()
        assertThat(trades[id]?.first())
            .usingRecursiveComparison()
            .ignoringFields("timestamp")
            .isEqualTo(trade)
    }

    @Test
    fun `Exception thrown when stock not found for trade`() {
        val id = "???"

        assertThatThrownBy { stockService.captureTrade(id, Indicator.BUY, 10, BigDecimal(50)) }
            .isInstanceOf(StockNotFoundException::class.java)
            .hasMessage("Stock $id not found.")
    }

    @Test
    fun `Calculate volume weighted stock price`() {
        loadTrades()
        assertThat(stockService.calculateVolumeWeightedStockPrice()).isEqualTo(BigDecimal(850.00).setScale(2))
    }

    @Test
    fun `Do not calculate VWAP when there are no trades`() {
        assertThatThrownBy { stockService.calculateVolumeWeightedStockPrice() }
            .isInstanceOf(TradeException::class.java)
            .hasMessage("No trades have been executed yet.")
    }

    @Test
    fun `Calculate share index`() {
        loadTrades()
        assertThat(stockService.calculateShareIndex()).isEqualTo(BigDecimal(65.11).setScale(2, RoundingMode.HALF_UP))
    }

    @Test
    fun `Do not calculate Share Index when there are no trades`() {
        assertThatThrownBy { stockService.calculateShareIndex() }
            .isInstanceOf(TradeException::class.java)
            .hasMessage("No trades have been executed yet.")
    }

    private fun loadStocks() {
        stocks.add(tea)
        stocks.add(coffee)
        stocks.add(milk)
        stocks.add(juice)
        stocks.add(water)
    }

    private fun loadTrades() {
        val trade1 =
            Trade(
                stock = milk,
                timestamp = LocalDateTime.now(),
                quantity = 5,
                indicator = Indicator.SELL,
                tradedPrice = BigDecimal(40),
            )
        val trade2 =
            Trade(
                stock = coffee,
                timestamp = LocalDateTime.now().minusMinutes(7),
                quantity = 20,
                indicator = Indicator.BUY,
                tradedPrice = BigDecimal(75),
            )
        val trade3 =
            Trade(
                stock = water,
                timestamp = LocalDateTime.now().minusMinutes(20),
                quantity = 30,
                indicator = Indicator.SELL,
                tradedPrice = BigDecimal(92),
            )

        trades[milk.identifier] = mutableListOf(trade1)
        trades[coffee.identifier] = mutableListOf(trade2)
        trades[water.identifier] = mutableListOf(trade3)
    }
}
