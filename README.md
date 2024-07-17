# Simple Stock Market

### Table of Contents
1. [Running the application](#running-the-application)
2. [Commands](#commands)
3. [Examples](#examples)
4. [Testing](#testing)

## Running the application
### IntelliJ
Simply right click on `SimpleStockMarketApplication.kt` and select run `Run 'SimpleStockMarketApplication.kt'`.

### Jar file
There is an executable .jar in the root of the project.
Enter `java -jar simple-stock-market.jar` to run the application.

## Commands
* **add**: Add a Stock
* **calculate-dividend-yield**: Calculate the dividend yield for a given Stock
* **calculate-pe-ratio**: Calculate the Price to Earnings Ratio for a given Stock
* **capture-trade**: Capture a Trade
* **calculate-vwap**: Calculate the Volume Weighted Stock Price based on trades in past 15 minutes
* **calculate-all-share-index**: Calculate the all Share Index for all Stocks
* **display-stocks**: Displays all Stocks
* **display-trades**: Displays all Trades, grouped by Stock
* **remove**: Remove a Stock

### Examples
**add**
```shell
add CAR ORDINARY 34.55 10
add TAT PREFERRED 22.78 120 8.9
```
**calculate-dividend-yield**
```shell
calculate-dividend-yield CAR 10.50
```
**calculate-pe-ratio**
```shell
calculate-pe-ratio CAR 15,77
```
**capture-trade**
```shell
capture-trade CAR BUY 50 19.22
```
**calculate-vwap**
```shell
calculate-vwap
```
**calculate-all-share-index**
```shell
calculate-all-share-index
```
**display-stocks**
```shell
display-stocks
```
**display-trades**
```shell
display-trades
```

## Testing
To run the tests, enter `./gradlew clean test`.