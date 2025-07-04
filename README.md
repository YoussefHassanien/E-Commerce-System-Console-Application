# E-Commerce System Console Application

A comprehensive Java-based console e-commerce system demonstrating object-oriented programming principles, design patterns, and clean architecture. This system handles multiple product types, shopping cart management, shipping calculations, and complete checkout processing.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Class Hierarchy](#class-hierarchy)
- [Getting Started](#getting-started)
- [Usage Examples](#usage-examples)
- [Test Cases](#test-cases)
- [Design Patterns](#design-patterns)
- [Documentation](#documentation)
- [Error Handling](#error-handling)
- [License](#license)

## 🎯 Overview

This e-commerce system is a console-based application that simulates real-world shopping scenarios. It supports different product types (regular, shippable, expirable), manages shopping carts, calculates shipping fees, validates customer balances, and generates detailed receipts. The system is built with extensibility and maintainability in mind, following SOLID principles and clean code practices.

## ✨ Features

### Product Management

- **Regular Products**: Digital items (e-books, software licenses, online courses)
- **Shippable Products**: Physical items requiring shipping with weight and shipping fee calculations
- **Expirable Products**: Items with expiration dates (food, medications, time-sensitive goods)
- **Expirable + Shippable Products**: Complex products that are both perishable and require shipping

### Shopping Cart System

- Add items with quantity validation against inventory
- Automatic price calculation and total tracking
- Inventory management with real-time stock updates
- Duplicate item handling with quantity merging
- Immutable cart state protection

### Shipping Service

- Automatic detection and filtering of shippable items
- Weight-based shipping calculations
- Consolidated shipping fees per order
- Package weight tracking and reporting
- Shipment notice generation

### Checkout System

- Customer balance validation before purchase
- Complete transaction processing
- Detailed receipt generation with itemized breakdown
- Shipment notices for physical products
- Error handling for insufficient funds

### Customer Management

- Customer profiles with name and balance tracking
- Input validation for customer data
- Balance sufficiency checks
- Purchase authorization

## 🏗️ Architecture

The system follows a **Layered Architecture** pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────┐
│                 Presentation Layer              │
│                   (Main.java)                   │
├─────────────────────────────────────────────────┤
│                 Business Logic Layer            │
│     (CheckoutService, ShippingService)          │
├─────────────────────────────────────────────────┤
│                   Service Layer                 │
│                (Cart, Customer)                 │
├─────────────────────────────────────────────────┤
│                   Model Layer                   │
│    (Product, Expirable, Shippable interfaces)   │
└─────────────────────────────────────────────────┘
```

### Architectural Benefits:

- **Maintainability**: Clear boundaries between layers
- **Testability**: Business logic isolated from presentation
- **Flexibility**: Easy to modify or extend individual components
- **Scalability**: Ready for future enhancements

## 📊 Class Hierarchy

```
Product (Base Class)
├── ShippableProduct (implements Shippable)
├── ExpirableProduct (implements Expirable)
└── ExpirableShippableProduct (implements Expirable, Shippable)

Interfaces:
├── Expirable
│   ├── setExpiryDate(LocalDate expiryDate)
│   └── isExpired()
└── Shippable
    ├── setWeight(double weight)
    ├── setShippingFees(double shippingFees)
    ├── getWeight()
    └── getShippingFees()

Core Services:
├── Cart
│   ├── addItem(Product, int)
│   ├── getTotalPrice()
│   └── getItems()
├── Customer
│   ├── getName()
│   └── getBalance()
├── ShippingService
│   ├── getTotalShippingFees()
│   ├── getTotalWeight()
│   └── getShippableItems()
└── CheckoutService
    ├── generateReceipt()
    └── isSufficientBalance()
```

## 🚀 Getting Started

### Prerequisites

- **Java 11 or higher**
- **IDE**: IntelliJ IDEA, Eclipse, or VS Code
- **Operating System**: Windows, macOS, or Linux

### Installation

1. **Clone or Download the Repository:**

```bash
git clone https://github.com/your-username/e-commerce-console-app.git
cd e-commerce-console-app
```

2. **Compile the Java Files:**

```bash
# For Windows (PowerShell)
javac *.java

# For macOS/Linux
javac *.java
```

3. **Run the Application:**

```bash
java Main
```

### Project Structure

```
E-Commerce-System-Console-Application/
├── Main.java                          # Application entry point with test cases
├── Product.java                       # Base product class
├── ShippableProduct.java             # Physical products with shipping
├── ExpirableProduct.java             # Products with expiry dates
├── ExpirableShippableProduct.java    # Complex products (expirable + shippable)
├── Expirable.java                     # Interface for expirable products
├── Shippable.java                     # Interface for shippable products
├── Cart.java                          # Shopping cart management
├── Customer.java                      # Customer data and validation
├── ShippingService.java              # Shipping calculations
├── CheckoutService.java              # Transaction processing and receipts
└── README.md                          # This documentation
```

## 💡 Usage Examples

### Creating Different Product Types

```java
// Regular digital product (no shipping, no expiry)
Product ebook = new Product("Java Programming Guide", 100, 29.99);

// Shippable physical product
ShippableProduct laptop = new ShippableProduct("Gaming Laptop", 5, 1299.99, 2.5, 25.99);
//                                               name, qty, price, weight(kg), shipping

// Expirable product (food, medicine)
ExpirableProduct milk = new ExpirableProduct("Organic Milk", 50, 4.99,
                                           LocalDate.now().plusDays(7));

// Complex product (both expirable and shippable)
ExpirableShippableProduct cheese = new ExpirableShippableProduct(
    "Aged Cheddar",                    // name
    20,                                // quantity
    12.99,                            // price
    LocalDate.now().plusDays(30),     // expiry date
    0.5,                              // weight in kg
    8.99                              // shipping fees
);
```

### Shopping Cart Operations

```java
Cart cart = new Cart();

// Add items to cart with quantity validation
boolean success1 = cart.addItem(ebook, 2);        // Add 2 ebooks
boolean success2 = cart.addItem(laptop, 1);       // Add 1 laptop
boolean success3 = cart.addItem(cheese, 3);       // Add 3 cheese blocks

// Check cart status
double totalPrice = cart.getTotalPrice();         // Get total price
boolean isEmpty = cart.isEmpty();                 // Check if cart is empty
int itemCount = cart.getItems().size();          // Number of different products

// Get all items (returns unmodifiable map for safety)
Map<Product, Integer> cartItems = cart.getItems();
```

### Customer Management

```java
// Create customer with validation
try {
    Customer customer = new Customer("John Doe", 1500.0, cart);
    System.out.println("Customer: " + customer.getName());
    System.out.println("Balance: $" + customer.getBalance());
} catch (IllegalArgumentException e) {
    System.out.println("Invalid customer data: " + e.getMessage());
}
```

### Shipping Service

```java
// Create shipping service for cart items
ShippingService shipping = new ShippingService(cart.getItems());

// Get shipping information
double totalShippingFees = shipping.getTotalShippingFees();
double totalWeight = shipping.getTotalWeight();
List<Shippable> shippableItems = shipping.getShippableItems();

System.out.println("Shipping fees: $" + totalShippingFees);
System.out.println("Total weight: " + totalWeight + "kg");
System.out.println("Shippable items: " + shippableItems.size());
```

### Complete Checkout Process

```java
try {
    // Create all components
    Cart cart = new Cart();
    cart.addItem(laptop, 1);
    cart.addItem(cheese, 2);

    Customer customer = new Customer("Alice Smith", 2000.0, cart);
    ShippingService shipping = new ShippingService(cart.getItems());

    // Process checkout (validates balance automatically)
    CheckoutService checkout = new CheckoutService(cart, customer, shipping);

    // Generate and print receipt
    checkout.generateReceipt();

} catch (IllegalArgumentException e) {
    System.out.println("Checkout failed: " + e.getMessage());
}
```

## 🧪 Test Cases

The application includes 10 comprehensive test cases covering all scenarios:

### Test Case Overview:

1. **Successful Mixed Products Checkout** - Regular + Shippable + Expirable products
2. **Digital-Only Products** - No shipping fees (ebooks, software, courses)
3. **Physical-Only Products** - Only shippable items with shipping calculations
4. **Expirable Products** - Date validation and expiry checking
5. **Complex Products** - Expirable + Shippable combinations
6. **Insufficient Balance** - Error handling for underfunded customers
7. **Empty Cart** - Validation scenarios for empty shopping carts
8. **Large Orders** - Multiple quantities and bulk purchasing
9. **Zero Balance Customer** - Edge case validation
10. **Exact Balance** - Customer has precisely enough money

### Running Tests

Execute the main class to run all test cases:

```bash
java Main
```

### Sample Test Output

```
=== E-Commerce System Test Cases ===

--- Test Case 1: Successful Mixed Products Checkout ---

** Shipment notice **
1x Laptop       2500g
3x Milk         0g
Total package weight 2.5kg

** Checkout receipt **
2x E-Book       50
1x Laptop       1000
3x Milk         18
----------------------
Subtotal        1068
Shipping        50
Amount          1118
```

### Error Handling Examples

```
--- Test Case 6: Insufficient Customer Balance ---
✅ Expected failure: Insufficient customer balance for this purchase
Customer needs more funds for this purchase.

--- Test Case 7: Empty Cart Scenario ---
✅ Expected failure: Cart cannot be empty
Cannot checkout with empty cart.
```

## 🎨 Design Patterns

### 1. Strategy Pattern

**Implementation**: Different product types with varying behaviors
**Benefit**: Easy to add new product types without modifying existing code

```java
// Interface segregation for different product capabilities
public interface Shippable {
    double getWeight();
    double getShippingFees();
}

public interface Expirable {
    boolean isExpired();
    LocalDate getExpiryDate();
}
```

### 2. Template Method Pattern

**Implementation**: Product validation with customizable business rules
**Benefit**: Consistent validation flow with type-specific rules

### 3. Defensive Programming Pattern

**Implementation**: Immutable collections and input validation
**Benefit**: Prevents external modification of internal state

```java
public Map<Product, Integer> getItems() {
    return Collections.unmodifiableMap(items); // Defensive copy
}
```

### 4. Factory Pattern (Implicit)

**Implementation**: Different product constructors for different types
**Benefit**: Centralized object creation with validation

### 5. Composite Pattern

**Implementation**: Cart containing multiple products with quantities
**Benefit**: Uniform handling of individual items and collections

## 📚 Documentation

### Core Classes

#### Product

```java
public class Product {
    // Constructor with validation
    public Product(String name, int quantity, double price)

    // Getters
    public String getName()
    public int getQuantity()
    public double getPrice()

    // Business methods
    public boolean reduceQuantity(int quantity)
}
```

#### Cart

```java
public class Cart {
    // Add items with inventory validation
    public boolean addItem(Product item, int quantity)

    // Get cart information
    public double getTotalPrice()
    public Map<Product, Integer> getItems()      // Unmodifiable view
    public boolean isEmpty()
}
```

#### Customer

```java
public class Customer {
    // Constructor with validation
    public Customer(String name, double balance, Cart cart)

    // Getters
    public String getName()
    public double getBalance()
}
```

#### ShippingService

```java
public class ShippingService {
    // Constructor processes cart items
    public ShippingService(Map<Product, Integer> items)

    // Shipping calculations
    public double getTotalShippingFees()
    public double getTotalWeight()
    public List<Shippable> getShippableItems()  // Unmodifiable list
}
```

#### CheckoutService

```java
public class CheckoutService {
    // Constructor validates all requirements
    public CheckoutService(Cart cart, Customer customer, ShippingService shippingService)

    // Receipt generation
    public void generateReceipt()               // Prints to console
}
```

### Interfaces

#### Expirable

```java
public interface Expirable {
    boolean setExpiryDate(LocalDate expiryDate);
    boolean isExpired();
}
```

#### Shippable

```java
public interface Shippable {
    boolean setWeight(double weight);
    boolean setShippingFees(double shippingFees);
    double getWeight();
    double getShippingFees();
}
```

## ⚠️ Error Handling

The system implements comprehensive error handling:

### Input Validation Errors

- **Invalid Product Name**: Null or empty names
- **Invalid Quantities**: Zero or negative quantities
- **Invalid Prices**: Zero or negative prices
- **Invalid Weights**: Zero or negative weights for shippable products
- **Invalid Dates**: Past expiry dates for expirable products

### Business Logic Errors

- **Insufficient Inventory**: Adding more items than available
- **Empty Cart**: Attempting checkout with empty cart
- **Insufficient Balance**: Customer cannot afford the purchase
- **Null References**: Null products, customers, or services

### Error Messages

All errors include descriptive messages for easy debugging:

```java
throw new IllegalArgumentException("Invalid name");
throw new IllegalArgumentException("Invalid quantity");
throw new IllegalArgumentException("Cart cannot be empty");
throw new IllegalArgumentException("Insufficient customer balance for this purchase");
```

## 🔧 Configuration

### Validation Rules

- **Product Names**: Must be non-null and non-empty (after trimming)
- **Quantities**: Must be positive integers
- **Prices**: Must be positive numbers
- **Weights**: Must be positive numbers (for shippable products)
- **Shipping Fees**: Must be positive numbers (for shippable products)
- **Expiry Dates**: Must be future dates (for expirable products)
- **Customer Balance**: Must be positive numbers
- **Customer Names**: Must be non-null and non-empty (after trimming)

### Business Rules

- Cart cannot be empty for checkout
- Customer balance must cover total cost (subtotal + shipping)
- Inventory must be sufficient for requested quantities
- Products with past expiry dates cannot be sold
- Shipping is only calculated for shippable products

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2025 E-Commerce Console Application

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 👨‍💻 Author

**Fawry Internship Candidate: Youssef Hassanien**

- Project: E-Commerce System Console Application
- Framework: Java Console Application
- Architecture: Layered Architecture with OOP Principles

## 🙏 Acknowledgments

- **Fawry Internship Program** - For providing this learning opportunity
- **Java Documentation** - Official Oracle Java documentation
- **Object-Oriented Design Principles** - Gang of Four design patterns
- **Clean Code** by Robert C. Martin - Code quality guidelines
- **Effective Java** by Joshua Bloch - Java best practices

**Note**: This is a console-based educational application demonstrating software engineering principles. It's designed for learning purposes and showcases object-oriented programming, design patterns, and clean architecture in Java. For production use, consider implementing proper security, database persistence, logging, and error handling mechanisms.
