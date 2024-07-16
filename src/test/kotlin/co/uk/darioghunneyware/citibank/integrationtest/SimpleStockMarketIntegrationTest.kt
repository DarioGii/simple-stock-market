package co.uk.darioghunneyware.citibank.integrationtest

import org.assertj.core.api.Assertions.assertThat
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.shell.test.ShellAssertions
import org.springframework.shell.test.ShellTestClient
import org.springframework.shell.test.ShellTestClient.InteractiveShellSession
import org.springframework.shell.test.autoconfigure.ShellTest
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@ShellTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SimpleStockMarketIntegrationTest {
    @Autowired
    private lateinit var client: ShellTestClient

    private lateinit var session: InteractiveShellSession

    @BeforeEach
    fun setUp() {
        session =
            client
                .interactive()
                .run()
    }

    @Test
    fun `List commands`() {
        val expectedCommands =
            """
            Stock Service
               add: Add a Stock
               calculate-pe-ratio: Calculate the Price to Earnings Ratio for a given Stock
               calculate-dividend-yield: Calculate the dividend yield for a given Stock
               display-stocks: Displays all Stocks
               display-trades: Displays all Trades, grouped by Stock
               capture-trade: Capture a Trade
               calculate-vwap: Calculate the Volume Weighted Stock Price based on trades in past 15 minutes
               calculate-all-share-index: Calculate the all Share Index for all Stocks
               remove: Remove a Stock
            """.trimIndent()

        ShellAssertions.assertThat(session.screen()).containsText("shell")

        session.write(
            session
                .writeSequence()
                .text("help")
                .carriageReturn()
                .build(),
        )
        await().atMost(2, TimeUnit.SECONDS).untilAsserted {
            val lines = session.screen().lines()
            lines.subList(12, 18).forEach {
                assertThat(expectedCommands).contains(it.trim())
            }
        }
    }
}
