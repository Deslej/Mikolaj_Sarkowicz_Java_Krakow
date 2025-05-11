# 🧠 Order Payment Optimizer

## 📋 Overview
Order Payment Optimizer is a Java-based utility that optimizes the way customer orders are paid using different payment methods. It selects the best possible payment method (or combination of methods) to maximize discounts and minimize cost while staying within the limits of each method.

## 📁 Project Structure
- **Main.java**: Entry point of the program
- **Optimizer.java**: Core logic for optimizing order payments
- **OrderMapper.java**: Parses JSON files into Order objects
- **PaymentMethodMapper.java**: Parses JSON files into PaymentMethod objects
- **Order.java**: Represents a customer order
- **PaymentMethod.java**: Represents a payment method including its discount and spending limit

## 🚀 How It Works

1. Orders are sorted from highest to lowest value
2. The program checks which payment methods can be used based on the order's value and the available promotions
3. It tries to apply the most beneficial single method or combination of methods to maximize savings
4. The result is a map of payment method IDs and the total amount spent using each

## 📦 Input

Two JSON files are required:

### orders.json
Contains a list of orders, each with:
- **id**: String
- **value**: Double
- **promotions**: List of applicable payment method IDs

### paymentmethods.json
Contains a list of available payment methods:
- **id**: String
- **discount**: Integer (percent)
- **limit**: Double (max amount that can be spent using this method)

## 🛠️ Usage

Compile and run the project using a command like:

```bash
java -jar path/to/app.jar path/to/orders.json path/to/paymentmethods.json
```

Example:
```bash
java -jar C:\Users\Mikołaj\Desktop\java\Mikolaj_Sarkowicz_Java_Krakow\target\app.jar C:\Users\Mikołaj\Downloads\Zadanie2025v2\orders.json C:\Users\Mikołaj\Downloads\Zadanie2025v2\paymentmethods.json
```

## ✅ Output

A list printed to the console showing how much money was spent using each payment method, for example:

```
PUNKTY  100.0
BosBankrut  190.0
mZysk  165.0
```