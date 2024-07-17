# Simple Stock Market

### Table of Contents
1. [Running the Application](#running-the-application)
    - [Using IntelliJ](#using-intellij)
    - [Using the Jar File](#using-a-jar-file)
2. [Commands](#commands)
3. [Examples](#examples)
   - [Adding Stocks](#adding-a-stock)
   - [Calculating Dividend Yield](#calculating-dividend-yield)
   - [Calculating P/E Ratio](#calculating-pe-ratio)
   - [Capturing Trades](#capturing-trades)
   - [Calculating Volume Weighted Stock Price](#calculating-volume-weighted-stock-price)
   - [Calculating All Share Index](#calculating-all-share-index)
   - [Displaying Stocks](#displaying-stocks)
   - [Displaying Trades](#displaying-trades)
   - [Removing Stocks](#removing-a-stock)
4. [Testing](#testing)

## Running the application
### Using IntelliJ
Simply right click on `SimpleStockMarketApplication.kt` and select run `Run 'SimpleStockMarketApplication.kt'`.

### Using a Jar file
There is an executable .jar in the root of the project.
Enter `java -jar simple-stock-market.jar` to run the application.

## Commands
* **add**: Add a Stock
  * `add <STOCK_IDENTIFIER> <ORDINARY|PREFERRED> <LAST_DIVIDEND> <PAR_VALUE>`
* **calculate-dividend-yield**: Calculate the dividend yield for a given Stock
  * `calculate-dividend-yield <STOCK_IDENTIFIER> <PRICE>`
* **calculate-pe-ratio**: Calculate the Price to Earnings Ratio for a given Stock
  * `calculate-pe-ratio <STOCK_IDENTIFIER> <PRICE>`
* **capture-trade**: Capture a Trade
  * `capture-trade <STOCK_IDENTIFIER> <BUY|SELL> <QUANTITY> <TRADED_PRICE>`
* **calculate-vwap**: Calculate the Volume Weighted Stock Price based on trades in past 15 minutes
* **calculate-all-share-index**: Calculate the all Share Index for all Stocks
* **display-stocks**: Displays all Stocks
* **display-trades**: Displays all Trades, grouped by Stock
* **remove**: Remove a Stock
  * `remove <STOCK_IDENTIFIER>`

### Examples
### Adding a Stock
```shell
add CAR ORDINARY 34.55 10
add TAT PREFERRED 22.78 120 8.9
```
### Calculating Dividend Yield
```shell
calculate-dividend-yield CAR 10.50
```
### Calculating P/E Ratio
```shell
calculate-pe-ratio CAR 15.77
```
### Capturing Trades
```shell
capture-trade CAR BUY 50 19.22
```
### Calculating Volume Weighted Stock Price
```shell
calculate-vwap
```
### Calculating All Share Index
```shell
calculate-all-share-index
```
### Displaying Stocks
```shell
display-stocks
```
### Displaying Trades
```shell
display-trades
```
### Removing a Stock
```shell
remove CAR
```

## Testing
To run the tests, enter `./gradlew clean test`.