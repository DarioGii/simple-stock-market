# Simple Stock Market

## Table of Contents
1. [Running the Application](#running-the-application)
   - [Using IntelliJ](#using-intellij)
   - [Using the Jar File](#using-the-jar-file)
2. [Commands](#commands)
3. [Examples](#examples)
   - [Adding Stocks](#adding-stocks)
   - [Calculating Dividend Yield](#calculating-dividend-yield)
   - [Calculating P/E Ratio](#calculating-pe-ratio)
   - [Capturing Trades](#capturing-trades)
   - [Calculating VWAP](#calculating-vwap)
   - [Calculating All Share Index](#calculating-all-share-index)
   - [Displaying Stocks](#displaying-stocks)
   - [Displaying Trades](#displaying-trades)
   - [Removing Stocks](#removing-stocks)
4. [Testing](#testing)
5. [Git Checkout Options](#git-checkout-options)

## Running the Application

### Using IntelliJ
1. Open IntelliJ and navigate to `SimpleStockMarketApplication.kt`.
2. Right-click on the file and select `Run 'SimpleStockMarketApplication.kt'`.

### Using the Jar File
1. In the root of the project, locate the executable `.jar` file.
2. Run the application by entering the following command:
   ```sh
   java -jar simple-stock-market.jar
   

## Commands

- **add**: Add a Stock
- **calculate-dividend-yield**: Calculate the dividend yield for a given Stock
- **calculate-pe-ratio**: Calculate the Price to Earnings Ratio for a given Stock
- **capture-trade**: Capture a Trade
- **calculate-vwap**: Calculate the Volume Weighted Stock Price based on trades in the past 15 minutes
- **calculate-all-share-index**: Calculate the All Share Index for all Stocks
- **display-stocks**: Display all Stocks
- **display-trades**: Display all Trades, grouped by Stock
- **remove**: Remove a Stock

## Examples

### Adding Stocks
```sh
add CAR ORDINARY 34.55 10
add TAT PREFERRED 22.78 120 8.9
````

### Calculating Dividend Yield
```sh
calculate-dividend-yield CAR 10.50
````

### Calculating P/E Ratio
```shell
calculate-pe-ratio CAR 15,77
```

### Capturing Trades
```shell
capture-trade CAR BUY 50 19.22
```
### Calculating VWAP
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

### Removing Stocks
```shell

```

## Testing
To run the tests, enter: 
```shell
./gradlew clean test
```
