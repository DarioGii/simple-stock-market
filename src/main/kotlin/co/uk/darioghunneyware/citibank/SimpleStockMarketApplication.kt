package co.uk.darioghunneyware.citibank

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SimpleStockMarketApplication

fun main(args: Array<String>) {
    runApplication<SimpleStockMarketApplication>(*args)
}
