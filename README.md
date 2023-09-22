= Currencycalculater

== Building

1. Clone the project:
```
git clone https://github.com/sametbakmaz/CurrencyCalculater.git
```
2.Navigate to the project:
```
cd CurrencyCalculator
```
3.To package your application:
```
./gradlew clean build
```

To run your application:
```
./gradlew run
```
# Usage
To use the service, follow these steps:

1. Visit the following URL in your browser:
```
http://localhost:7010/currencyCalculate/{amount}/{from}/{to}
```
Example:
```
http://localhost:7010/currencyCalculate/100/USD/EUR
```
You will receive the result in JSON format:
```
{
  "result": 87.23,
  "resultCurrency": "EUR"
}
```
